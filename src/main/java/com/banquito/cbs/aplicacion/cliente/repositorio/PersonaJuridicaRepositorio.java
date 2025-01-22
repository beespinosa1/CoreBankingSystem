package com.banquito.cbs.aplicacion.cliente.repositorio;

import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaJuridicaRepositorio extends JpaRepository<PersonaJuridica, Integer>
{
    Optional<PersonaJuridica> findByRuc(String ruc);
    List<PersonaJuridica> findByPersonaNatural(PersonaNatural personaNatural);
}
