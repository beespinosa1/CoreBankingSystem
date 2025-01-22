package com.banquito.cbs.aplicacion.autenticacion.service;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.repository.UsuarioRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio
{
    private final UsuarioRepositorio usuarioRepositorio;

    private static final String ESTADO_ACTIVO = "ACT";
    private static final String ESTADO_INACTIVO = "INA";
    private static final String ESTADO_BLOQUEADO = "BLO";

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> getAll() {
        return usuarioRepositorio.findAll();
    }

    public Usuario find(Integer id) {
        return usuarioRepositorio.findById(id).orElseThrow(() -> new EntidadNoEncontradaExcepcion("Uusario no registrado"));
    }

    public void crear(Usuario usuario) {
        usuario.setEstado(UsuarioServicio.ESTADO_ACTIVO);
        usuario.setContrasenia(UtilidadHash.hashString(usuario.getContrasenia()));
        usuarioRepositorio.save(usuario);
    }

    public void editar(Usuario usuario, String contrasenia) {
        usuario.setContrasenia(UtilidadHash.hashString(contrasenia));
        usuarioRepositorio.save(usuario);
    }

    public void eliminar(Usuario usuario) {
        usuarioRepositorio.delete(usuario);
    }
}
