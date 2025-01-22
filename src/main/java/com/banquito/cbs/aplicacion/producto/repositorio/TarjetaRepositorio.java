package com.banquito.cbs.aplicacion.producto.repositorio;

import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarjetaRepositorio extends JpaRepository<Tarjeta, Integer> {
    List<Tarjeta> findByClienteId(Integer clienteId);
    Optional<Tarjeta> findByNumero(String numero);
    Optional<Tarjeta> findTopByOrderByFechaCreacionDesc();
}
