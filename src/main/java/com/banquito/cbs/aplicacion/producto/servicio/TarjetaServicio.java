package com.banquito.cbs.aplicacion.producto.servicio;

import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.repositorio.TarjetaRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadCriptografia;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TarjetaServicio {
    private final TarjetaRepositorio repositorio;

    public static final String TIPO_CREDITO = "CRE";
    public static final String TIPO_DEBITO = "DEB";

    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";

    public TarjetaServicio(TarjetaRepositorio tarjetaRepositorio) {
        this.repositorio = tarjetaRepositorio;
    }

    public List<Tarjeta> listar() {
        return repositorio.findAll();
    }

    public List<Tarjeta> listarPorCliente(Integer clienteId) {
        return repositorio.findByClienteId(clienteId);
    }

    public Tarjeta buscarPorId(Integer id) {
        return repositorio.findById(id).orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ninguna tarjeta con ID: " + id));
    }

    public Tarjeta buscarPorNumero(String numeroTarjeta) {
        return repositorio.findByNumero(numeroTarjeta).orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ninguna tarjeta con número: " + numeroTarjeta));
    }

    public void crearTarjeta(Tarjeta tarjeta) throws Exception {
        tarjeta.setNumero(this.generarNuevoNumeroTarjeta()); // TODO: OBTENER DATOS DE LA MARCA
        tarjeta.setCvv(UtilidadHash.hashString("123")); // TODO: OBTENER DATOS DE LA MARCA
        tarjeta.setFechaExpiracion(LocalDate.now().plusYears(5)); // TODO: OBTENER DATOS DE LA MARCA
        tarjeta.setFechaEmision(LocalDate.now());
        tarjeta.setEstado(ESTADO_ACTIVA);
        tarjeta.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        tarjeta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        repositorio.save(tarjeta);
    }

    public void actualizarTarjeta(Tarjeta tarjeta) {
        tarjeta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        repositorio.save(tarjeta);
    }

    public void activarTarjeta(Tarjeta tarjeta) {
        tarjeta.setEstado(ESTADO_ACTIVA);
        tarjeta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        repositorio.save(tarjeta);
    }

    public void inactivarTarjeta(Tarjeta tarjeta) {
        tarjeta.setEstado(ESTADO_INACTIVA);
        tarjeta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        repositorio.save(tarjeta);
    }

    private String generarNuevoNumeroTarjeta() {
        Tarjeta tarjeta = repositorio.findTopByOrderByFechaCreacionDesc().orElse(null);
        BigInteger numero = (tarjeta == null)
                ? BigInteger.ONE
                : new BigInteger(tarjeta.getNumero().substring(6)).add(BigInteger.ONE);

        BigInteger maxNumero = new BigInteger("999999999999");
        if (numero.compareTo(maxNumero) > 0) {
            throw new OperacionInvalidaExcepcion("No se pueden generar más números de tarjeta");
        }

        String prefixVisa = "411111";
        return prefixVisa + String.format("%010d", numero);
    }
}
