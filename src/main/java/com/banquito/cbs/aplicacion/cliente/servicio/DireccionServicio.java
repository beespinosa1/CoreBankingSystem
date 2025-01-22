package com.banquito.cbs.aplicacion.cliente.servicio;

import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.repositorio.DireccionRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionServicio
{
    private final DireccionRepositorio repositorio;
    public static final String ENTITY_NAME = "Direccion";

    public DireccionServicio(DireccionRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Direccion buscarPorId(Integer id)
    {
        return this.repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
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
