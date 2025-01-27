package com.banquito.cbs.aplicacion.cliente.controlador;

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

import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaJuridicaAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaJuridicaDto;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.PersonaJuridicaMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaJuridicaPeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaJuridicaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("v1/personas-juridicas")
@CrossOrigin("*")
public class PersonaJuridicaControlador {

    private final PersonaJuridicaServicio servicio;
    private final PersonaJuridicaAdaptador adaptador;
    private final PersonaJuridicaMapper mapper;

    public PersonaJuridicaControlador(PersonaJuridicaServicio servicio, PersonaJuridicaAdaptador adaptador,
            PersonaJuridicaMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar personas jurídicas", description = "Devuelve una lista de todas las personas jurídicas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas jurídicas obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<PersonaJuridicaDto>> listar() {
        try {
            List<PersonaJuridica> personas = this.servicio.listar();
            List<PersonaJuridicaDto> dtos = new ArrayList<>(personas.size());
            for (PersonaJuridica persona : personas) {
                dtos.add(mapper.toDto(persona));
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error al listar las personas jurídicas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Obtener una persona jurídica por su ID", description = "Devuelve los detalles de una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona jurídica encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaJuridicaDto> mostrar(
            @Parameter(description = "ID de la persona jurídica que se desea buscar", required = true) @PathVariable("id") Integer id) {
        try {
            PersonaJuridica persona = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDto(persona));
        } catch (NotFoundException nfe) {
            // System.err.println(nfe.getMessage());
            log.error("Persona jurídica no encontrada: " + id, nfe);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar una persona jurídica por identificación", description = "Devuelve los detalles de una persona jurídica según su número de identificación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona jurídica encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<PersonaJuridicaDto> buscarPorIdentificacion(
            @Parameter(description = "Número de identificación de la persona jurídica", required = true) 
            @PathVariable("identificacion") String identificacion) {
        try {
            PersonaJuridica persona = this.servicio.buscarPorRuc(identificacion);
            return ResponseEntity.ok(this.mapper.toDto(persona));
        } catch (NotFoundException nfe) {
            log.error("Persona jurídica con identificación {} no encontrada", identificacion, nfe);
            return ResponseEntity.notFound().build();
        }
    }

    // No hay excepción explícita
    @Operation(summary = "Crear una nueva persona jurídica", description = "Permite registrar una nueva persona jurídica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona jurídica creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(campos);
        servicio.crear(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(persona));
    }

    // No hay excepción explícita
    @Operation(summary = "Modificar una persona jurídica existente", description = "Permite actualizar los datos de una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona jurídica actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDto.class))),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @Parameter(description = "ID de la persona jurídica que se desea actualizar", required = true) @PathVariable Integer id,
            @Valid @RequestBody PersonaJuridicaPeticion campos) {
        try {
            PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(id, campos);
            this.servicio.actualizar(persona);
            return ResponseEntity.ok(mapper.toDto(persona));
        } catch (Exception e) {
            log.error("Error al modificar persona jurídica con ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Eliminar una persona jurídica", description = "Permite eliminar una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona jurídica eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la persona jurídica que se desea eliminar", required = true) @PathVariable Integer id) {
        try {
            this.servicio.eliminar(servicio.buscarPorId(id));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException nfe) {
            log.error("Persona jurídica con ID " + id + " no encontrada", nfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error al eliminar persona jurídica con ID " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
