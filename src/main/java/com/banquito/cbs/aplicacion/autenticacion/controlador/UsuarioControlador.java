package com.banquito.cbs.aplicacion.autenticacion.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.cbs.aplicacion.autenticacion.controlador.dto.UsuarioDto;
import com.banquito.cbs.aplicacion.autenticacion.controlador.mapper.UsuarioMapper;
import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.service.UsuarioServicio;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/usuarios")
@CrossOrigin("*")
@Slf4j
public class UsuarioControlador {

    private final UsuarioServicio servicio;
    private final UsuarioMapper mapper;

    public UsuarioControlador(UsuarioServicio servicio, UsuarioMapper mapper) {
        this.servicio = servicio;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Recupera una lista de todos los usuarios registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listar() {
        try {
            List<Usuario> usuarios = this.servicio.listar();
            List<UsuarioDto> dtos = new ArrayList<>(usuarios.size());
            for (Usuario usuario : usuarios) {
                dtos.add(mapper.toDto(usuario));
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error al listar usuarios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener usuario por ID", description = "Recupera los detalles de un usuario específico por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> mostrar(
            @Parameter(description = "ID del usuario a recuperar", required = true) @PathVariable("id") Integer id) {
        try {
            Usuario usuario = this.servicio.find(id);
            return ResponseEntity.ok(this.mapper.toDto(usuario));
        } catch (NotFoundException nfe) {
            log.error("Usuario no encontrado: {}", id, nfe);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<UsuarioDto> crear(@Valid @RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario usuario = this.mapper.toModel(usuarioDto);
            this.servicio.crear(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(usuario));
        } catch (Exception e) {
            log.error("Error al crear usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Integer id,
            @Valid @RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario usuario = this.mapper.toModel(usuarioDto);
            usuario.setId(id);
            this.servicio.editar(usuario, usuario.getContrasenia());
            return ResponseEntity.ok(mapper.toDto(usuario));
        } catch (NotFoundException nfe) {
            log.error("Usuario no encontrado: {}", id, nfe);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al actualizar usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Integer id) {
        try {
            Usuario usuario = this.servicio.find(id);
            this.servicio.eliminar(usuario);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException nfe) {
            log.error("Usuario no encontrado: {}", id, nfe);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al eliminar usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
