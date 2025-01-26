package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaJuridicaDTO;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaJuridicaAdaptador;
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

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/personas-juridicas")
@CrossOrigin("*")
public class PersonaJuridicaControlador {

    private final PersonaJuridicaServicio servicio;
    private final PersonaJuridicaAdaptador adaptador;
    private final PersonaJuridicaMapper mapper;

    public PersonaJuridicaControlador(PersonaJuridicaServicio servicio, PersonaJuridicaAdaptador adaptador, PersonaJuridicaMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar personas jurídicas", description = "Devuelve una lista de todas las personas jurídicas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de personas jurídicas obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PersonaJuridicaDTO>> listar() {
        List<PersonaJuridica> personas = this.servicio.listar();
        List<PersonaJuridicaDTO> dtos = new ArrayList<>(personas.size());
        for (PersonaJuridica persona : personas) {
            dtos.add(mapper.toDTO(persona));
        }
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Obtener una persona jurídica por su ID", description = "Devuelve los detalles de una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona jurídica encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonaJuridicaDTO> mostrar(
            @Parameter(description = "ID de la persona jurídica que se desea buscar", required = true) @PathVariable("id") Integer id) {
        try {
            PersonaJuridica persona = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(persona));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva persona jurídica", description = "Permite registrar una nueva persona jurídica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Persona jurídica creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(campos);
        servicio.crear(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(persona));
    }

    @Operation(summary = "Modificar una persona jurídica existente", description = "Permite actualizar los datos de una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona jurídica actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaJuridicaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @Parameter(description = "ID de la persona jurídica que se desea actualizar", required = true) @PathVariable Integer id,
            @Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(id, campos);
        this.servicio.actualizar(persona);
        return ResponseEntity.ok(mapper.toDTO(persona));
    }

    @Operation(summary = "Eliminar una persona jurídica", description = "Permite eliminar una persona jurídica específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Persona jurídica eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Persona jurídica no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la persona jurídica que se desea eliminar", required = true) @PathVariable Integer id) {
        this.servicio.eliminar(servicio.buscarPorId(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
