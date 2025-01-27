package com.banquito.cbs.aplicacion.producto.servicio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import com.banquito.cbs.aplicacion.producto.cliente.MarcaCliente;
import com.banquito.cbs.aplicacion.producto.dto.CrearTarjetaDto;
import com.banquito.cbs.aplicacion.producto.dto.TarjetaMarcaDto;
import com.banquito.cbs.aplicacion.producto.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.repositorio.TarjetaRepositorio;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TarjetaServicio {
    private final TarjetaRepositorio repositorio;
    private final MarcaCliente cliente;
    private final ClienteServicio clienteServicio;

    public static final String ENTITY_NAME = "Tarjeta";

    public static final String TIPO_CREDITO = "CRD";
    public static final String TIPO_DEBITO = "DEB";

    public static final String ESTADO_ACTIVA = "ACT";
    public static final String ESTADO_INACTIVA = "INA";

    public TarjetaServicio(
            TarjetaRepositorio tarjetaRepositorio,
            MarcaCliente cliente,
            ClienteServicio clienteServicio
    ) {
        this.repositorio = tarjetaRepositorio;
        this.cliente = cliente;
        this.clienteServicio = clienteServicio;
    }

    public List<Tarjeta> listar() {
        return repositorio.findAll();
    }

    public List<Tarjeta> listarPorCliente(Integer clienteId) {
        List<Tarjeta> tarjetas = repositorio.findByClienteId(clienteId);
        if (tarjetas.isEmpty()) {
            throw new NotFoundException(clienteId.toString(), ENTITY_NAME);
        }
        return tarjetas;
    }

    public Tarjeta buscarPorId(Integer id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
    }

    public Tarjeta buscarPorNumero(String numeroTarjeta) {
        return repositorio.findByNumero(numeroTarjeta)
                .orElseThrow(() -> new NotFoundException(numeroTarjeta, ENTITY_NAME));
    }

    public void crearTarjeta(Cliente cliente, String tipo, String franquicia, BigDecimal cupo, Integer diaCorte) {
        CrearTarjetaDto peticion = new CrearTarjetaDto();

        String identificacion = cliente.getTipo().equals(ClienteServicio.PERSONA)
            ? cliente.getPersonaNatural().getIdentificacion()
            : cliente.getPersonaJuridica().getRuc()
        ;

        String nombre = cliente.getTipo().equals(ClienteServicio.PERSONA)
            ? cliente.getPersonaNatural().getPrimerNombre() + " "  + cliente.getPersonaNatural().getSegundoNombre() + " "
                + cliente.getPersonaNatural().getPrimerApellido() + " "  + cliente.getPersonaNatural().getSegundoApellido()
            : cliente.getPersonaJuridica().getRazonSocial()
        ;

        Direccion direccion = cliente.getTipo().equals(ClienteServicio.PERSONA)
            ? cliente.getPersonaNatural().getDirecciones().get(0)
            : cliente.getPersonaJuridica().getDirecciones().get(0)
        ;

        String direccionStr = direccion.getProvincia() + ", "
            + direccion.getCiudad() + ", "
            + direccion.getCanton() + ", "
            + direccion.getSector() + ", "
            + direccion.getCallePrincipal() + ", "
            + direccion.getNumero() + " y "
            + direccion.getCalleSecundaria()
        ;

        String telefono = cliente.getTipo().equals(ClienteServicio.PERSONA)
            ? cliente.getPersonaNatural().getNumeroTelefonico()
            : cliente.getPersonaJuridica().getNumeroTelefonico()
        ;

        String correo = cliente.getTipo().equals(ClienteServicio.PERSONA)
            ? cliente.getPersonaNatural().getEmail()
            : cliente.getPersonaJuridica().getEmail()
        ;

        peticion.setIdentificacion(identificacion);
        peticion.setNombre(nombre.toUpperCase());
        peticion.setDireccion(direccionStr);
        peticion.setTelefono(telefono);
        peticion.setCorreo(correo);
        peticion.setTipo(tipo);
        peticion.setFranquicia(franquicia);

        log.info("Solicitud creación de tarjeta de crédito {}", peticion);
        TarjetaMarcaDto tarjetaMarca = this.cliente.crearTarjeta(peticion);

        Tarjeta tarjeta = new Tarjeta();

        tarjeta.setClienteId(cliente.getId());
        tarjeta.setFechaEmision(tarjetaMarca.getFechaEmision());
        tarjeta.setNumero(tarjetaMarca.getNumero());
        tarjeta.setCupoAprobado(cupo);
        tarjeta.setCupoDisponible(cupo);
        tarjeta.setDiaCorte(diaCorte);
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
