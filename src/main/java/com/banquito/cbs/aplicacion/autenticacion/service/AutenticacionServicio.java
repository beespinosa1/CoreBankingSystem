package com.banquito.cbs.aplicacion.autenticacion.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.repository.UsuarioRepositorio;
import com.banquito.cbs.compartido.excepciones.EntidadNoEncontradaExcepcion;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadHash;

@Service
public class AutenticacionServicio
{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Map<String, Object> verificarUsuario(String username)
    {
        Usuario usuario = this.usuarioRepositorio.findByUsuario(username).orElseThrow(() -> new EntidadNoEncontradaExcepcion("Usuario no existe"));
        Map<String, Object> datos = new HashMap<>();
        datos.put("esValido", true);
        datos.put("imagen", usuario.getImg());

        return datos;
    }

    public Map<String, Object> login(String username, String password)
    {
        Usuario usuario = this.usuarioRepositorio.findByUsuario(username)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Usuario no existe"));

        if (!UtilidadHash.verificarString(password, usuario.getContrasenia()))
            throw new OperacionInvalidaExcepcion("Contraseña incorrecta");

        usuario.setFechaUltimoIngreso(LocalDateTime.now());
        this.usuarioRepositorio.save(usuario);

        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("id", usuario.getId());
        datosUsuario.put("clienteId", usuario.getClienteId());
        datosUsuario.put("usuario", usuario.getUsuario());
        datosUsuario.put("rol", usuario.getRol());
        datosUsuario.put("img", usuario.getImg());
        datosUsuario.put("fechaUltimoIngreso", usuario.getFechaUltimoIngreso());
        
        if (usuario.getCliente() != null) {
            Map<String, Object> datosCliente = new HashMap<>();
            datosCliente.put("tipo", usuario.getCliente().getTipo());
            datosCliente.put("estado", usuario.getCliente().getEstado());
            
            if (usuario.getCliente().getPersonaNatural() != null) {
                Map<String, Object> personaNatural = new HashMap<>();
                personaNatural.put("primerNombre", usuario.getCliente().getPersonaNatural().getPrimerNombre());
                personaNatural.put("segundoNombre", usuario.getCliente().getPersonaNatural().getSegundoNombre());
                personaNatural.put("primerApellido", usuario.getCliente().getPersonaNatural().getPrimerApellido());
                personaNatural.put("segundoApellido", usuario.getCliente().getPersonaNatural().getSegundoApellido());
                datosCliente.put("personaNatural", personaNatural);
            } else if (usuario.getCliente().getPersonaJuridica() != null) {
                Map<String, Object> personaJuridica = new HashMap<>();
                personaJuridica.put("razonSocial", usuario.getCliente().getPersonaJuridica().getRazonSocial());
                datosCliente.put("personaJuridica", personaJuridica);
            }
            
            datosUsuario.put("cliente", datosCliente);
        }
        System.out.println("Datos completos: " + datosUsuario);
        return datosUsuario;
    }

    public void logout(String sessionToken)
    {
        //
    }
}
