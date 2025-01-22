package com.banquito.cbs.aplicacion.cliente.servicio;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.repositorio.PersonaNaturalRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadDuplicadaExcepcion;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaNaturalServicio {
    private final PersonaNaturalRepositorio repositorio;
    private final ClienteServicio clienteServicio;

    public PersonaNaturalServicio(PersonaNaturalRepositorio repositorio, ClienteServicio clienteServicio) {
        this.repositorio = repositorio;
        this.clienteServicio = clienteServicio;
    }

    public List<PersonaNatural> listar() {
        return this.repositorio.findAll();
    }

    public PersonaNatural buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion(
                        "No existe ningún registro con el id: " + id));
    }

    public PersonaNatural buscarPorIdentificacion(String identificacion) {
        return this.repositorio.findByIdentificacion(identificacion)
            .orElseThrow(() -> new EntidadNoEncontradaExcepcion(
                "No existe ningún registro con el número de identificación: " + identificacion));
    }

    public void crear(PersonaNatural persona) {
        if (this.repositorio.findByIdentificacion(persona.getIdentificacion()).isPresent())
            throw new EntidadDuplicadaExcepcion("Ya existe un registro con el número de identificación: " + persona.getIdentificacion());

        persona.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        persona.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        this.repositorio.save(persona);

        Cliente cliente = new Cliente();
        cliente.setPersonaNaturalId(persona.getId());
        cliente.setIngresoPromedioMensual(BigDecimal.valueOf(0.00));

        this.clienteServicio.registrarClienteNatural(cliente);
    }

    public void actualizar(PersonaNatural persona) {
        Optional<PersonaNatural> auxPersona = repositorio.findByIdentificacion(persona.getIdentificacion());
        if (auxPersona.isPresent() && !auxPersona.get().equals(persona))
            throw new OperacionInvalidaExcepcion("Ya existe un registro con el número de identificación: " + persona.getIdentificacion());

        persona.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));
        this.repositorio.save(persona);
    }

    public void eliminar(PersonaNatural persona) {
        this.repositorio.deleteById(persona.getId());
    }
}
