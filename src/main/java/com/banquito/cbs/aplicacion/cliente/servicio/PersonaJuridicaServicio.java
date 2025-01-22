package com.banquito.cbs.aplicacion.cliente.servicio;

import com.banquito.cbs.aplicacion.cliente.excepcion.DuplicateException;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.repositorio.PersonaJuridicaRepositorio;
/* import com.banquito.cbs.compartido.excepciones.EntidadDuplicadaExcepcion;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;*/
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PersonaJuridicaServicio
{
    private final PersonaJuridicaRepositorio repositorio;
    private final ClienteServicio clienteServicio;
    public static final String ENTITY_NAME = "PersonaJuridica";

    public PersonaJuridicaServicio(PersonaJuridicaRepositorio repositorio, ClienteServicio clienteServicio)
    {
        this.repositorio = repositorio;
        this.clienteServicio = clienteServicio;
    }

    public List<PersonaJuridica> listar() {
        return this.repositorio.findAll();
    }

    public PersonaJuridica buscarPorId(Integer id) {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
    }

    public PersonaJuridica buscarPorRuc(String ruc)
    {
        return repositorio.findByRuc(ruc)
            .orElseThrow(() -> new NotFoundException(ruc, ENTITY_NAME));
    }

    public List<PersonaJuridica> buscarPorPersonaNatural(PersonaNatural personaNatural)
    {
        return this.repositorio.findByPersonaNatural(personaNatural);
    }

    public void crear(PersonaJuridica persona)
    {
        if (repositorio.findByRuc(persona.getRuc()).isPresent())
            throw new DuplicateException(persona.getRuc(), ENTITY_NAME);

        persona.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        persona.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        this.repositorio.save(persona);

        Cliente cliente = new Cliente();
        cliente.setPersonaJuridicaId(persona.getId());
        cliente.setIngresoPromedioMensual(BigDecimal.valueOf(0.00));

        this.clienteServicio.registrarClienteCorporativo(cliente);
    }

    public void actualizar(PersonaJuridica persona)
    {
        if (!repositorio.findByRuc(persona.getRuc()).isPresent())
            throw new OperacionInvalidaExcepcion("No existe un registro con el RUC: " + persona.getRuc());
        repositorio.save(persona);
    }

    public void eliminar(PersonaJuridica persona) {
        this.repositorio.deleteById(persona.getId());
    }
}
