package com.banquito.cbs.aplicacion.externo.servicio;

import com.banquito.cbs.aplicacion.externo.modelo.SeguridadMarca;
import com.banquito.cbs.aplicacion.externo.repositorio.SeguridadMarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguridadMarcaServicio
{
    @Autowired
    private SeguridadMarcaRepositorio seguridadMarcaRepositorio;

    public List<SeguridadMarca> getAll() {
        return seguridadMarcaRepositorio.findAll();
    }

    public SeguridadMarca find(Integer id) {
        return seguridadMarcaRepositorio.findById(id).orElse(null);
    }

    public void save(SeguridadMarca customer) {
        seguridadMarcaRepositorio.save(customer);
    }

    public void destroy(SeguridadMarca customer) {
        seguridadMarcaRepositorio.delete(customer);
    }
}
