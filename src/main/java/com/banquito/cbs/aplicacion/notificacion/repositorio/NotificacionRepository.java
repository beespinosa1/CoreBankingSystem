package com.banquito.cbs.aplicacion.notificacion.repositorio;

import com.banquito.cbs.aplicacion.notificacion.modelo.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
