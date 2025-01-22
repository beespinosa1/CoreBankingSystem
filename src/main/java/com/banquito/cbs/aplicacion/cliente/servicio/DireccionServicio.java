package com.banquito.cbs.aplicacion.cliente.servicio;

import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.repositorio.DireccionRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DireccionServicio
{
    private final DireccionRepositorio repositorio;

    public DireccionServicio(DireccionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Direccion buscarPorId(Integer id)
    {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("No existe ningún con ID: " + id));
    }

    public List<Direccion> buscarPorPersonaNatural(PersonaNatural personaNatural)
    {
        return repositorio.findByPersonaNatural(personaNatural);
    }

    public List<Direccion> buscarPorPersonaJuridica(PersonaJuridica personaJuridica)
    {
        return repositorio.findByPersonaJuridica(personaJuridica);
    }

    public void crear(Direccion direccion)
    {
        repositorio.save(direccion);
    }

    public void actualizar(Direccion direccion)
    {
        repositorio.save(direccion);
    }

    public void eliminar(Direccion direccion)
    {
        repositorio.delete(direccion);
    }
}
