package com.banquito.cbs.aplicacion.autenticacion.service;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.repository.UsuarioRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AutenticacionServicio
{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Map<String, Object> verificarUsuario(String username)
    {
        Usuario usuario = this.usuarioRepositorio.findByUsuario(username).orElseThrow(() -> new EntidadNoEncontradaExcepcion("Usuario no existe"));
        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put("esValido", true);
        datos.put("imagen", usuario.getImg());

        return datos;
    }

    public Usuario login(String username, String password)
    {
        Usuario usuario = this.usuarioRepositorio.findByUsuario(username).orElseThrow(() -> new EntidadNoEncontradaExcepcion("Usuario no existe"));

        if (!UtilidadHash.verificarString(password, usuario.getContrasenia()))
            throw new OperacionInvalidaExcepcion("Contraseña incorrecta");

        usuario.setFechaUltimoIngreso(LocalDateTime.now());
        this.usuarioRepositorio.save(usuario);

        return usuario;
    }

    public void logout(String sessionToken)
    {
        //
    }
}
