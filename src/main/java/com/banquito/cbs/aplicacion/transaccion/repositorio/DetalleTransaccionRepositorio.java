package com.banquito.cbs.aplicacion.transaccion.repositorio;

import com.banquito.cbs.aplicacion.transaccion.modelo.DetalleTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleTransaccionRepositorio extends JpaRepository<DetalleTransaccion, Integer> {
}
