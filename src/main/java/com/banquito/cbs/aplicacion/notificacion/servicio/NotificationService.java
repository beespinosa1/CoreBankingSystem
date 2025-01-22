package com.banquito.cbs.aplicacion.notificacion.servicio;

import com.banquito.cbs.aplicacion.notificacion.modelo.Notificacion;
import com.banquito.cbs.aplicacion.notificacion.repositorio.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> getAll() {
        return notificacionRepository.findAll();
    }

    public Notificacion find(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public void save(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }

    public void destroy(Notificacion notificacion) {
        notificacionRepository.delete(notificacion);
    }
}
