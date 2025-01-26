package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.DireccionDto;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.DireccionAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.DireccionMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.DireccionPeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import com.banquito.cbs.aplicacion.cliente.servicio.DireccionServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("v1/direcciones")
@CrossOrigin("*")
public class DireccionControlador {

        private final ClienteServicio clienteServicio;
        private final DireccionServicio servicio;
        private final DireccionAdaptador adaptador;
        private final DireccionMapper mapper;

        public DireccionControlador(DireccionServicio servicio, DireccionAdaptador adaptador,
                        ClienteServicio clienteServicio, DireccionMapper mapper) {
                this.clienteServicio = clienteServicio;
                this.servicio = servicio;
                this.adaptador = adaptador;
                this.mapper = mapper;
        }

        @Operation(summary = "Listar direcciones de un cliente", description = "Devuelve una lista de direcciones asociadas a un cliente especificado por su ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Direcciones encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDto.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<DireccionDto>> listar(
                        @Parameter(description = "ID del cliente cuyas direcciones se desean listar", required = true) @RequestParam("idCliente") Integer idCliente) {
                try {
                        Cliente cliente = clienteServicio.buscarPorId(idCliente);
                        List<Direccion> direcciones = (cliente.getTipo().equals(ClienteServicio.CORPORATIVO))
                                        ? this.servicio.buscarPorPersonaJuridica(cliente.getPersonaJuridica())
                                        : this.servicio.buscarPorPersonaNatural(cliente.getPersonaNatural());

                        List<DireccionDto> dtos = new ArrayList<>(direcciones.size());
                        for (Direccion direccion : direcciones) {
                                dtos.add(mapper.toDto(direccion));
                        }
                        return ResponseEntity.ok(dtos);
                } catch (NotFoundException nfe) {
                        log.error("Error al listar direcciones: Cliente con ID {} no encontrado.", idCliente, nfe);
                        throw nfe; // Re-lanzar la excepción si es necesario
                } catch (Exception e) {
                        log.error("Error inesperado al listar direcciones para el cliente con ID {}.", idCliente, e);
                        throw e; // Manejo adicional si hay errores genéricos
                }
        }

        @Operation(summary = "Obtener una dirección por su ID", description = "Devuelve los detalles de una dirección específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Dirección encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDto.class))),
                        @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<DireccionDto> mostrar(
                        @Parameter(description = "ID de la dirección que se desea buscar", required = true) @PathVariable("id") Integer id) {
                try {
                        Direccion direccion = this.servicio.buscarPorId(id);
                        return ResponseEntity.ok(this.mapper.toDto(direccion));
                } catch (NotFoundException nfe) {
                        // System.err.println(nfe.getMessage());
                        log.error("Dirección no encontrada para el ID: {}", id, nfe);
                        return ResponseEntity.notFound().build();
                }
        }

        // Error gestionado por Valid
        @Operation(summary = "Crear una nueva dirección", description = "Permite registrar una nueva dirección asociada a un cliente.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Dirección creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDto.class))),
                        @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<?> crear(
                        @Valid @RequestBody DireccionPeticion peticion) {
                Direccion direccion = this.adaptador.peticionADireccion(peticion);
                this.servicio.crear(direccion);
                return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(direccion));
        }

        @Operation(summary = "Editar una dirección existente", description = "Permite actualizar los datos de una dirección específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Dirección actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDto.class))),
                        @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<?> editar(
                        @Parameter(description = "ID de la dirección que se desea actualizar", required = true) @PathVariable Integer id,
                        @Valid @RequestBody DireccionPeticion peticion) {
                try {
                        Direccion direccion = this.adaptador.peticionADireccion(id, peticion);
                        this.servicio.actualizar(direccion);
                        return ResponseEntity.ok(mapper.toDto(direccion));
                } catch (Exception e) {
                        // Log de error si algo sale mal
                        log.error("Error al intentar actualizar la dirección con ID: " + id, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
        }

        @Operation(summary = "Eliminar una dirección", description = "Permite eliminar una dirección específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Dirección eliminada exitosamente", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminar(
                        @Parameter(description = "ID de la dirección que se desea eliminar", required = true) @PathVariable Integer id) {
                try {
                        Direccion direccion = servicio.buscarPorId(id);
                        if (direccion == null) {
                                return ResponseEntity.notFound().build();
                        }
                        this.servicio.eliminar(direccion);
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                } catch (Exception e) {
                        log.error("Error al intentar eliminar la dirección con ID: " + id, e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
        }

}
