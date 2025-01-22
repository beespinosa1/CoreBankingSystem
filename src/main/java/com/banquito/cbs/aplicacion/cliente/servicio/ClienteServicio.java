package com.banquito.cbs.aplicacion.cliente.servicio;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.repositorio.ClienteRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ClienteServicio
{
    private final ClienteRepositorio repositorio;

    public static final String CORPORATIVO = "COR";
    public static final String PERSONA = "PER";

    public static final String ACTIVO = "ACT";
    public static final String INACTIVO = "INA";

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente buscarPorId(Integer id)
    {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ningún cliente con ID: " + id));
    }

    public Cliente buscarPorPersonaNatural(PersonaNatural personaNatural)
    {
        return repositorio.findByPersonaNatural(personaNatural)
            .orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ningún registro para: " + personaNatural));
    }

    public Cliente buscarPorPersonaJuridica(PersonaJuridica personaJuridica)
    {
        return repositorio.findByPersonaJuridica(personaJuridica)
            .orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ningún registro para: " + personaJuridica));
    }

    public void registrarClienteCorporativo(Cliente cliente)
    {
        cliente.setTipo(ClienteServicio.CORPORATIVO);
        cliente.setEstado(ClienteServicio.ACTIVO);

        cliente.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        cliente.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        repositorio.save(cliente);
    }

    public void registrarClienteNatural(Cliente cliente)
    {
        cliente.setTipo(ClienteServicio.PERSONA);
        cliente.setEstado(ClienteServicio.ACTIVO);

        cliente.setFechaCreacion(LocalDateTime.now(ZoneId.systemDefault()));
        cliente.setFechaActualizacion(LocalDateTime.now(ZoneId.systemDefault()));

        repositorio.save(cliente);
    }

    public void actualizar(Cliente cliente)
    {
        repositorio.save(cliente);
    }
}
