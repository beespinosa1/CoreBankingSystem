package com.banquito.cbs.aplicacion.autenticacion.controlador;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.service.UsuarioServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/usuarios")
@CrossOrigin("*")
public class UsuarioControlador {

    private final UsuarioServicio servicio;

    public UsuarioControlador(UsuarioServicio servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Recupera una lista de todos los usuarios registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.getAll()));
    }

    @Operation(summary = "Obtener usuario por ID", description = "Recupera los detalles de un usuario específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@Parameter(description = "ID del usuario a recuperar", required = true) 
                                     @PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.find(id)));
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> almacenar(@Valid @RequestBody Usuario usuario) {
        this.servicio.crear(usuario);

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario registrado exitosamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(datos));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Parameter(description = "Datos del usuario a actualizar", required = true) 
                                    @RequestBody Usuario usuario, @PathVariable("id") Integer id) {
        Usuario usuarioActual = this.servicio.find(id);
        this.servicio.editar(usuarioActual, usuario.getContrasenia());

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario actualizado exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(datos));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@Parameter(description = "ID del usuario a eliminar", required = true) 
                                      @PathVariable("id") Integer id) {
        this.servicio.eliminar(this.servicio.find(id));

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario eliminado exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(datos));
    }
}
