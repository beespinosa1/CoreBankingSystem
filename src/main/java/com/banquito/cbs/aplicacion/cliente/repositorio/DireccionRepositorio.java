package com.banquito.cbs.aplicacion.cliente.repositorio;

import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DireccionRepositorio extends JpaRepository<Direccion, Integer>
{
    List<Direccion> findByPersonaNatural(PersonaNatural personaNatural);
    List<Direccion> findByPersonaJuridica(PersonaJuridica personaNatural);
}
