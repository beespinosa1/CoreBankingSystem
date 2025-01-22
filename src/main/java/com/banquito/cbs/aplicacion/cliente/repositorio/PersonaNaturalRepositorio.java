package com.banquito.cbs.aplicacion.cliente.repositorio;

import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaNaturalRepositorio extends JpaRepository<PersonaNatural, Integer>
{
    Optional<PersonaNatural> findByIdentificacion(String identificacion);
}
