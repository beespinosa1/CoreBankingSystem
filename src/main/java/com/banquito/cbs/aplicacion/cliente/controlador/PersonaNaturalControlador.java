package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaNaturalDTO;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaNaturalAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.PersonaNaturalMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaNaturalPeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaNaturalServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("v1/personas-naturales")
@CrossOrigin("*")
public class PersonaNaturalControlador {

    private final PersonaNaturalServicio servicio;
    private final PersonaNaturalAdaptador adaptador;
    private final PersonaNaturalMapper mapper;

    public PersonaNaturalControlador(PersonaNaturalServicio servicio, PersonaNaturalAdaptador adaptador,
            PersonaNaturalMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PersonaNaturalDTO>> listar() {
        try {
            List<PersonaNatural> personas = this.servicio.listar();
            List<PersonaNaturalDTO> dtos = new ArrayList<>(personas.size());
            for (PersonaNatural persona : personas) {
                dtos.add(mapper.toDTO(persona));
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error al listar personas naturales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener una persona natural por su ID", description = "Devuelve los detalles de una persona natural específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona natural encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaNaturalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona natural no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaNaturalDTO> mostrar(
            @Parameter(description = "ID de la persona natural que se desea buscar", required = true) @PathVariable("id") Integer id) {
        try {
            PersonaNatural persona = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(persona));
        } catch (NotFoundException nfe) {
            // System.err.println(nfe.getMessage());
            log.error("Persona natural con ID {} no encontrada", id, nfe);
            return ResponseEntity.notFound().build();
        }
    }

    // No se maneja excepciones
    @Operation(summary = "Crear una nueva persona natural", description = "Permite registrar una nueva persona natural.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona natural creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaNaturalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody PersonaNaturalPeticion campos) {
        PersonaNatural persona = this.adaptador.peticionAPersonaNatural(campos);
        servicio.crear(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(persona));
    }

    @Operation(summary = "Modificar una persona natural existente", description = "Permite actualizar los datos de una persona natural específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona natural actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaNaturalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona natural no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @Parameter(description = "ID de la persona natural que se desea actualizar", required = true) @PathVariable Integer id,
            @Valid @RequestBody PersonaNaturalPeticion campos) {
        try {
            PersonaNatural persona = this.adaptador.peticionAPersonaNatural(id, campos);
            this.servicio.actualizar(persona);
            return ResponseEntity.ok(mapper.toDTO(persona));
        } catch (Exception e) {
            log.error("Error al modificar persona natural con ID " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @Operation(summary = "Eliminar una persona natural", description = "Permite eliminar una persona natural específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona natural eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona natural no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la persona natural que se desea eliminar", required = true) @PathVariable Integer id) {
        try {
            this.servicio.eliminar(servicio.buscarPorId(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException nfe) {
            log.error("Persona natural con ID " + id + " no encontrada para eliminar", nfe);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al eliminar persona natural con ID " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
