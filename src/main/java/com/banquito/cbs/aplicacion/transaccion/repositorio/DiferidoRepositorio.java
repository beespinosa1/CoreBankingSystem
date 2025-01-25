package com.banquito.cbs.aplicacion.transaccion.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.cbs.aplicacion.transaccion.modelo.Diferido;

public interface DiferidoRepositorio extends JpaRepository<Diferido, Integer> {
    
}
