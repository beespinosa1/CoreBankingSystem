package com.banquito.cbs.aplicacion.cliente.repositorio;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>
{
    Optional<Cliente> findByPersonaNatural(PersonaNatural personaNatural);
    Optional<Cliente> findByPersonaJuridica(PersonaJuridica personaNatural);
}
