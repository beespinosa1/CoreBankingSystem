package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.DireccionDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/direcciones")
@CrossOrigin("*")
public class DireccionControlador {

    private final ClienteServicio clienteServicio;
    private final DireccionServicio servicio;
    private final DireccionAdaptador adaptador;
    private final DireccionMapper mapper;

    public DireccionControlador(DireccionServicio servicio, DireccionAdaptador adaptador, ClienteServicio clienteServicio, DireccionMapper mapper) {
        this.clienteServicio = clienteServicio;
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar direcciones de un cliente", description = "Devuelve una lista de direcciones asociadas a un cliente especificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Direcciones encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<DireccionDTO>> listar(
            @Parameter(description = "ID del cliente cuyas direcciones se desean listar", required = true) @RequestParam("idCliente") Integer idCliente) {
        Cliente cliente = clienteServicio.buscarPorId(idCliente);
        List<Direccion> direcciones = (cliente.getTipo().equals(ClienteServicio.CORPORATIVO))
                ? this.servicio.buscarPorPersonaJuridica(cliente.getPersonaJuridica())
                : this.servicio.buscarPorPersonaNatural(cliente.getPersonaNatural());

        List<DireccionDTO> dtos = new ArrayList<>(direcciones.size());
        for (Direccion direccion : direcciones) {
            dtos.add(mapper.toDTO(direccion));
        }
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Obtener una dirección por su ID", description = "Devuelve los detalles de una dirección específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> mostrar(
            @Parameter(description = "ID de la dirección que se desea buscar", required = true) @PathVariable("id") Integer id) {
        try {
            Direccion direccion = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(direccion));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva dirección", description = "Permite registrar una nueva dirección asociada a un cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dirección creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crear(
            @Valid @RequestBody DireccionPeticion peticion) {
        Direccion direccion = this.adaptador.peticionADireccion(peticion);
        this.servicio.crear(direccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(direccion));
    }

    @Operation(summary = "Editar una dirección existente", description = "Permite actualizar los datos de una dirección específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DireccionDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @Parameter(description = "ID de la dirección que se desea actualizar", required = true) @PathVariable Integer id,
            @Valid @RequestBody DireccionPeticion peticion) {
        Direccion direccion = this.adaptador.peticionADireccion(id, peticion);
        this.servicio.actualizar(direccion);
        return ResponseEntity.ok(mapper.toDTO(direccion));
    }

    @Operation(summary = "Eliminar una dirección", description = "Permite eliminar una dirección específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dirección eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la dirección que se desea eliminar", required = true) @PathVariable Integer id) {
        this.servicio.eliminar(servicio.buscarPorId(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
