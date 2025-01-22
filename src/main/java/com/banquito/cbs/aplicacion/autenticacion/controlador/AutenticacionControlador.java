package com.banquito.cbs.aplicacion.autenticacion.controlador;

import com.banquito.cbs.aplicacion.autenticacion.controlador.peticion.IniciarSesionPeticion;
import com.banquito.cbs.aplicacion.autenticacion.controlador.peticion.UsuarioValidacionPeticion;
import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.service.AutenticacionServicio;
import com.banquito.cbs.aplicacion.autenticacion.service.UsuarioServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class AutenticacionControlador {
    private final AutenticacionServicio servicio;
    private final UsuarioServicio usuarioServicio;

    public AutenticacionControlador(AutenticacionServicio servicio, UsuarioServicio usuarioServicio) {
        this.servicio = servicio;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/verificar-usuario")
    public ResponseEntity<?> verificarUsuario(@Valid @RequestBody UsuarioValidacionPeticion peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.verificarUsuario(peticion.getUsuario())));
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody IniciarSesionPeticion peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.login(peticion.getUsuario(), peticion.getContrasenia())));
    }
}
