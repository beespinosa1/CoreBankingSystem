package com.banquito.cbs.aplicacion.autenticacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.repository.UsuarioRepositorio;
import com.banquito.cbs.aplicacion.cliente.excepcion.DuplicateException;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;

@Service
public class UsuarioServicio
{
    private final UsuarioRepositorio repositorio;
    public static final String ENTITY_NAME = "Usuario";

    private static final String ESTADO_ACTIVO = "ACT";
    private static final String ESTADO_INACTIVO = "INA";
    private static final String ESTADO_BLOQUEADO = "BLO";

    public UsuarioServicio(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Usuario> listar() {
        return repositorio.findAll();
    }

    public Usuario find(Integer id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), ENTITY_NAME));
    }

    public void crear(Usuario usuario) {
        if (repositorio.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new DuplicateException(usuario.getUsuario(), ENTITY_NAME);
        }
        
        usuario.setEstado(ESTADO_ACTIVO);
        usuario.setContrasenia(UtilidadHash.hashString(usuario.getContrasenia()));
        repositorio.save(usuario);
    }

    public void editar(Usuario usuario, String contrasenia) {
        if (!repositorio.findByUsuario(usuario.getUsuario()).isPresent()) {
            throw new NotFoundException(usuario.getUsuario(), ENTITY_NAME);
        }
        
        usuario.setContrasenia(UtilidadHash.hashString(contrasenia));
        repositorio.save(usuario);
    }

    public void eliminar(Usuario usuario) {
        repositorio.delete(usuario);
    }
}
