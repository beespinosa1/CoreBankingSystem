package com.banquito.cbs.aplicacion.producto.servicio;

import com.banquito.cbs.aplicacion.producto.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.repositorio.CuentaRepositorio;
import com.banquito.cbs.aplicacion.transaccion.servicio.TransaccionServicio;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class CuentaServicio {
    private final CuentaRepositorio repositorio;
    private final TransaccionServicio transaccionServicio;
    public static final String ENTITY_NAME = "Cuenta";

    public static final String TIPO_AHORROS = "AHO";
    public static final String TIPO_CORRIENTE = "COR";

    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";

    public CuentaServicio(CuentaRepositorio cuentaRepositorio, @Lazy TransaccionServicio transaccionServicio) {
        this.repositorio = cuentaRepositorio;
        this.transaccionServicio = transaccionServicio;
    }

    public List<Cuenta> listar() {
        return repositorio.findAll();
    }

    public List<Cuenta> listarPorCliente(Integer clienteId) {
        List<Cuenta> cuentas = repositorio.findByClienteId(clienteId);
        if (cuentas.isEmpty()) {
            throw new NotFoundException(clienteId.toString(), ENTITY_NAME);
        }
        return cuentas;
    }

    public Cuenta buscarPorId(Integer id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
    }

    public Cuenta buscarPorNumero(String numeroCuenta) {
        return this.repositorio.findByNumero(numeroCuenta)
                .orElseThrow(() -> new NotFoundException(numeroCuenta, ENTITY_NAME));
    }

    public void crearCuenta(Cuenta cuenta, BigDecimal depositoInicial) {
        if (!cuenta.getTipo().equals(CuentaServicio.TIPO_AHORROS) && !cuenta.getTipo().equals(CuentaServicio.TIPO_CORRIENTE))
            throw new OperacionInvalidaExcepcion("Tipo de cuenta no valido");

        cuenta.setNumero(this.generarNuevoNuemroCuenta());
        cuenta.setSaldoAcreditar(BigDecimal.ZERO);
        cuenta.setSaldoDisponible(BigDecimal.ZERO);
        cuenta.setEstado(CuentaServicio.ESTADO_ACTIVA);

        cuenta.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        this.actualizarTotalCuenta(cuenta);

        repositorio.save(cuenta);

        this.transaccionServicio.crearDeposito(cuenta, depositoInicial, TransaccionServicio.CANAL_WEB, "Deposito Inicial");
    }

    public void depositarValores(Cuenta cuenta, BigDecimal valor) {
        cuenta.setSaldoAcreditar(cuenta.getSaldoAcreditar().add(valor));
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        this.actualizarTotalCuenta(cuenta);
        repositorio.save(cuenta);
    }

    public void preAcreditarValores(Cuenta cuenta, BigDecimal valor) {
        cuenta.setSaldoAcreditar(cuenta.getSaldoAcreditar().add(valor));
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        this.actualizarTotalCuenta(cuenta);
        repositorio.save(cuenta);
    }

    public void acreditarValores(Cuenta cuenta) {
        cuenta.setSaldoTotal(cuenta.getSaldoTotal().add(cuenta.getSaldoAcreditar()));
        cuenta.setSaldoAcreditar(BigDecimal.ZERO);
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        this.actualizarTotalCuenta(cuenta);
        repositorio.save(cuenta);
    }

    public void activarCuenta(Cuenta cuenta) {
        cuenta.setEstado(CuentaServicio.ESTADO_ACTIVA);
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        repositorio.save(cuenta);
    }

    public void inactivarCuenta(Cuenta cuenta) {
        cuenta.setEstado(CuentaServicio.ESTADO_INACTIVA);
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        repositorio.save(cuenta);
    }

    private String generarNuevoNuemroCuenta()
    {
        Cuenta cuenta = this.repositorio.findTopByOrderByFechaCreacionDesc().orElse(null);
        BigInteger numero = (cuenta == null)
                ? BigInteger.ONE
                : new BigInteger(cuenta.getNumero()).add(BigInteger.ONE)
        ;

        BigInteger maxNumero = new BigInteger("9999999999999999");
        if (numero.compareTo(maxNumero) > 0)
            throw new OperacionInvalidaExcepcion("No se pueden generar más números de cuenta");

        return String.format("%08d", numero);
    }

    public void actualizarTotalCuenta(Cuenta cuenta) {
        cuenta.setSaldoTotal(cuenta.getSaldoDisponible().add(cuenta.getSaldoAcreditar()));
        cuenta.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
    }
}
