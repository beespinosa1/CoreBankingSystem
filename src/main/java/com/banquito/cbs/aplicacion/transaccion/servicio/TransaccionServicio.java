package com.banquito.cbs.aplicacion.transaccion.servicio;

import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.servicio.CuentaServicio;
import com.banquito.cbs.aplicacion.producto.servicio.TarjetaServicio;
import com.banquito.cbs.aplicacion.transaccion.dto.ItemDetalleTransaccionDto;
import com.banquito.cbs.aplicacion.transaccion.excepcion.FraudeExcepcion;
import com.banquito.cbs.aplicacion.transaccion.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.transaccion.modelo.ItemComision;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import com.banquito.cbs.aplicacion.transaccion.modelo.DetalleTransaccion;
import com.banquito.cbs.aplicacion.transaccion.repositorio.TransaccionRepositorio;
import com.banquito.cbs.aplicacion.transaccion.repositorio.DetalleTransaccionRepositorio;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TransaccionServicio {
    private final TransaccionRepositorio repositorio;
    private final DetalleTransaccionRepositorio detalleTransaccionRepositorio;

    private final TarjetaServicio tarjetaServicio;
    private final CuentaServicio cuentaServicio;
    private final DiferidoServcio diferidoServicio;

    public static final String ENTITY_NAME = "Transaccion";

    private static final String TIPO_DEPOSITO = "DEP";
    private static final String TIPO_PAGO_TARJETA = "PTC";
    private static final String TIPO_CONSUMO = "CNS";
    private static final String TIPO_REVERSO = "REV";
    private static final String TIPO_TRANSFERENCIA_INTERNA = "TRI";
    private static final String TIPO_TRANSFERENCIA_EXTERNA = "TRE";

    public static final String CANAL_WEB = "WEB";
    public static final String CANAL_MOVIL = "MOV";
    public static final String CANAL_POS = "EXT";

    private static final String ESTADO_APROBADA = "APR";
    private static final String ESTADO_RECHAZADA = "REC";
    private static final String ESTADO_PENDIENTE = "PEN";
    private static final String ESTADO_REVERSADA = "REV";
    private static final String ESTADO_ANULADA = "ANU";

    private static final BigDecimal INTERES_CONSUMO = BigDecimal.valueOf(16.5);
    private static final BigDecimal PORCENTAJE_COMISION_CONSUMO = BigDecimal.valueOf(0.001);

    public TransaccionServicio(TransaccionRepositorio repositorio,
                                DetalleTransaccionRepositorio detalleTransaccionRepositorio,
                                TarjetaServicio tarjetaServicio,
                                CuentaServicio cuentaServicio,
                                DiferidoServcio diferidoServicio
    ) {
        this.repositorio = repositorio;
        this.detalleTransaccionRepositorio = detalleTransaccionRepositorio;
        this.tarjetaServicio = tarjetaServicio;
        this.cuentaServicio = cuentaServicio;
        this.diferidoServicio = diferidoServicio;
    }

    public List<Transaccion> listarPorTarjeta(Integer tarjetaId)
    {
        List<Transaccion> transacciones = this.repositorio.findByTarjetaId(tarjetaId);
        if (transacciones.isEmpty()) {
            throw new NotFoundException(tarjetaId.toString(), ENTITY_NAME);
        }
        return transacciones;
    }

    public List<Transaccion> listarPorCuenta(Integer cuentaId)
    {
        List<Transaccion> transacciones = this.repositorio.findByCuentaId(cuentaId);
        if (transacciones.isEmpty()) {
            throw new NotFoundException(cuentaId.toString(), ENTITY_NAME);
        }
        return transacciones;
    }

   /*  public Transaccion buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
    } */

    @Transactional
    public void crearDeposito(Cuenta cuenta, BigDecimal valor, String canal, String descripcion)
    {
        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaId(cuenta.getId());
        transaccion.setTipo(TransaccionServicio.TIPO_DEPOSITO);
        transaccion.setCanal(canal);
        transaccion.setValor(valor);
        transaccion.setComision(BigDecimal.ZERO);
        transaccion.setTazaInteres(BigDecimal.ZERO);
        transaccion.setEsDiferido(false);
        transaccion.setEstado(TransaccionServicio.ESTADO_APROBADA);
        transaccion.setFechaHora(LocalDate.now());

        repositorio.save(transaccion);

        DetalleTransaccion detalle = new DetalleTransaccion();
        detalle.setTransaccionId(transaccion.getId());
        detalle.setDescripcion(descripcion);

        detalleTransaccionRepositorio.save(detalle);

        this.cuentaServicio.depositarValores(cuenta, valor);
    }

    @Transactional
    public void crearPagoTarjetaCredito(Integer tarjetaId, BigDecimal valor, String canal, String descripcion) {
        Transaccion transaccion = new Transaccion();
        transaccion.setTarjetaId(tarjetaId);
        transaccion.setTipo(TransaccionServicio.TIPO_PAGO_TARJETA);
        transaccion.setCanal(canal);
        transaccion.setValor(valor);
        transaccion.setComision(BigDecimal.ZERO);
        transaccion.setTazaInteres(BigDecimal.ZERO);
        transaccion.setEsDiferido(false);
        transaccion.setEstado(TransaccionServicio.ESTADO_PENDIENTE);
        transaccion.setFechaHora(LocalDate.now());

        repositorio.save(transaccion);

        // Detalle de la transacción
        DetalleTransaccion detalle = new DetalleTransaccion();
        detalle.setTransaccionId(transaccion.getId());
        detalle.setDescripcion(descripcion);

        detalleTransaccionRepositorio.save(detalle);
    }

    @Transactional
    public void crearTransferenciaInterna(Integer cuentaOrigenId, Integer cuentaDestinoId, BigDecimal valor, String canal, String descripcion) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaId(cuentaOrigenId);
        transaccion.setTipo(TransaccionServicio.TIPO_TRANSFERENCIA_INTERNA);
        transaccion.setTazaInteres(BigDecimal.ZERO);
        transaccion.setEstado(TransaccionServicio.ESTADO_APROBADA);
        transaccion.setFechaHora(LocalDate.now());

        repositorio.save(transaccion);

        // Detalle de la transacción
        DetalleTransaccion detalle = new DetalleTransaccion();
        detalle.setTransaccionId(transaccion.getId());
        detalle.setCuentaDestino(cuentaDestinoId.toString());
        detalle.setDescripcion(descripcion);

        detalleTransaccionRepositorio.save(detalle);
    }

    @Transactional
    public void crearTransferenciaExterna(Integer cuentaOrigenId, String binBancoDestino, BigDecimal valor, String canal, String descripcion) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaId(cuentaOrigenId);
        transaccion.setTipo(TransaccionServicio.TIPO_TRANSFERENCIA_EXTERNA);
        transaccion.setCanal(canal);
        transaccion.setValor(valor);
        transaccion.setComision(BigDecimal.ZERO);
        transaccion.setTazaInteres(BigDecimal.ZERO);
        transaccion.setEsDiferido(false);
        transaccion.setEstado(TransaccionServicio.ESTADO_PENDIENTE);
        transaccion.setFechaHora(LocalDate.now());

        repositorio.save(transaccion);

        // Detalle de la transacción
        DetalleTransaccion detalle = new DetalleTransaccion();
        detalle.setTransaccionId(transaccion.getId());
        detalle.setBinBancoDestino(binBancoDestino);
        detalle.setDescripcion(descripcion);

        detalleTransaccionRepositorio.save(detalle);
    }

    @Transactional
    public void registrarConsumoTarjeta(Transaccion transaccion,
                                        String numeroTarjeta,
                                        String descripcion,
                                        String numeroCuenta,
                                        Boolean tieneIntereses,
                                        Integer cuotas
    ) {
        Tarjeta tarjeta = this.tarjetaServicio.buscarPorNumero(numeroTarjeta);

        if (tarjeta.getEstado().equals(TarjetaServicio.ESTADO_INACTIVA))
            throw new OperacionInvalidaExcepcion("La tarjeta no tiene autorizado transaccionar");

        if (tarjeta.getCupoDisponible().compareTo(transaccion.getValor()) < 0)
            throw new OperacionInvalidaExcepcion("La tarjeta no cuenta con el cupo necesario para la transacción");

        if (transaccion.getEsDiferido())
            transaccion.setTazaInteres(tieneIntereses ? TransaccionServicio.INTERES_CONSUMO : BigDecimal.ZERO);
        else
            transaccion.setTazaInteres(BigDecimal.ZERO);

        transaccion.setTarjetaId(tarjeta.getId());
        transaccion.setTipo(TransaccionServicio.TIPO_CONSUMO);
        transaccion.setComision(this.calcularComisionBanco(transaccion.getValor()));
        transaccion.setEstado(TransaccionServicio.ESTADO_PENDIENTE);
        transaccion.setFechaHora(LocalDate.now());
        transaccion.setCanal(TransaccionServicio.CANAL_POS);

        transaccion.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        transaccion.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        log.info("Transaccion: {}", transaccion);
        repositorio.save(transaccion);

        DetalleTransaccion detalle = new DetalleTransaccion();
        detalle.setTransaccionId(transaccion.getId());
        detalle.setCuentaDestino(numeroCuenta);
        detalle.setDescripcion(descripcion);
        log.info("Detalle transaccion: {}", detalle);

        detalleTransaccionRepositorio.save(detalle);

        tarjeta.setCupoDisponible(tarjeta.getCupoDisponible().subtract(transaccion.getValor()));
        log.info("Tarjeta: {}", tarjeta);
        this.tarjetaServicio.actualizarTarjeta(tarjeta);

        if (transaccion.getEsDiferido())
            this.diferidoServicio.crearDiferido(transaccion, cuotas);
    }

    private BigDecimal calcularComisionBanco(BigDecimal valor) {
        return valor.multiply(TransaccionServicio.PORCENTAJE_COMISION_CONSUMO);
    }

    public BigDecimal cobrarComisionesConsumo(Transaccion transaccion, Map<String, Object> detalle)
    {
        BigDecimal valorFinal = transaccion.getValor();

        ItemDetalleTransaccionDto comisionGateway = (ItemDetalleTransaccionDto) detalle.get("gtw");
        ItemDetalleTransaccionDto comisionProcessor = (ItemDetalleTransaccionDto) detalle.get("processor");
        ItemDetalleTransaccionDto comisionMarca = (ItemDetalleTransaccionDto) detalle.get("marca");

        valorFinal = valorFinal.subtract(comisionGateway.getComision());
        valorFinal = valorFinal.subtract(comisionProcessor.getComision());
        valorFinal = valorFinal.subtract(comisionProcessor.getComision());
        valorFinal = valorFinal.subtract(transaccion.getComision());

        Cuenta cuentaGateway = this.cuentaServicio.buscarPorNumero(comisionGateway.getNumeroCuenta());
        Cuenta cuentaProcessor = this.cuentaServicio.buscarPorNumero(comisionProcessor.getNumeroCuenta());
        Cuenta cuentaMarca = this.cuentaServicio.buscarPorNumero(comisionMarca.getNumeroCuenta());

        this.cuentaServicio.preAcreditarValores(cuentaGateway, comisionGateway.getComision());
        this.cuentaServicio.preAcreditarValores(cuentaProcessor, comisionProcessor.getComision());
        this.cuentaServicio.preAcreditarValores(cuentaMarca, comisionMarca.getComision());

        return valorFinal;
    }

    public void enviarDineroBeneficiarioConsumo(Transaccion transaccion, BigDecimal valor) {
        DetalleTransaccion detalle = this.detalleTransaccionRepositorio.findByTransaccionId(transaccion.getId());
        Cuenta cuenta = this.cuentaServicio.buscarPorNumero(detalle.getCuentaDestino());

        this.cuentaServicio.preAcreditarValores(cuenta, valor);
    }

    public void validarTransaccion(Transaccion transaccion, String numeroTarjeta, String cvv, String fechaCaducidad,
                                   String descripcion, String numeroCuenta, String beneficiario) {
        Tarjeta tarjeta = this.tarjetaServicio.buscarPorNumero(numeroTarjeta);

        if (!UtilidadHash.verificarString(cvv, tarjeta.getCvv()))
            throw new OperacionInvalidaExcepcion("Código de seguridad de la tarjeta incorrecto");

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth fechaEntrada = YearMonth.parse(fechaCaducidad, inputFormatter);;
        YearMonth fechaBaseDatos = YearMonth.from(tarjeta.getFechaExpiracion());

        if (!fechaEntrada.equals(fechaBaseDatos))
            throw new OperacionInvalidaExcepcion("La fecha de caducidad de la tarjeta no coincide");

        if (tarjeta.getCupoDisponible().compareTo(transaccion.getValor()) < 0)
            throw new OperacionInvalidaExcepcion("La tarjeta no cuenta con el cupo necesario para la transacción");

        List<Transaccion> transaccionesRecientes = repositorio.buscarTransaccionesRecientes(
                tarjeta.getId(), descripcion, numeroCuenta, beneficiario, LocalDateTime.now().minusMinutes(10));

        if (!transaccionesRecientes.isEmpty())
            throw new FraudeExcepcion("Transacción similar detectada en los últimos 10 minutos. Posible fraude.");
    }
}
