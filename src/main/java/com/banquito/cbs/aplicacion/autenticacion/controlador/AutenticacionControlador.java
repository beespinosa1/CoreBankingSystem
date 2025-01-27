package com.banquito.cbs.aplicacion.autenticacion.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.cbs.aplicacion.autenticacion.controlador.peticion.IniciarSesionPeticion;
import com.banquito.cbs.aplicacion.autenticacion.controlador.peticion.UsuarioValidacionPeticion;
import com.banquito.cbs.aplicacion.autenticacion.service.AutenticacionServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("v1")
public class AutenticacionControlador {

    private final AutenticacionServicio servicio;

    public AutenticacionControlador(AutenticacionServicio servicio) {
        this.servicio = servicio;
    }

    
    @Operation(summary = "Verificar si un usuario existe en el sistema", description = "Verifica la existencia de un usuario por nombre.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario verificado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/verificar-usuario")
    public ResponseEntity<?> verificarUsuario(
            @Parameter(description = "Petición que contiene el nombre de usuario a verificar.") 
            @RequestBody UsuarioValidacionPeticion peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.verificarUsuario(peticion.getUsuario())));
    }
    
    @Operation(summary = "Iniciar sesión en el sistema", description = "Inicia sesión en el sistema con las credenciales proporcionadas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> iniciarSesion(
            @Parameter(description = "Petición que contiene las credenciales del usuario.") 
            @RequestBody IniciarSesionPeticion peticion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.login(peticion.getUsuario(), peticion.getContrasenia())));
    }
}
