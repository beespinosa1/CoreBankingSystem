package com.banquito.cbs.aplicacion.producto.repositorio;

import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Integer> {
    List<Cuenta> findByClienteId(Integer clienteId);
    Optional<Cuenta> findByNumero(String numero);
    Optional<Cuenta> findTopByOrderByFechaCreacionDesc();
}
