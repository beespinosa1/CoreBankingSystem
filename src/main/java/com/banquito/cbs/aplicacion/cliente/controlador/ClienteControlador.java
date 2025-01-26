package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.ClienteDto;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.ClienteAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.ClienteMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.ClientePeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/clientes")
@CrossOrigin("*")
public class ClienteControlador {

    private final ClienteServicio servicio;
    private final ClienteAdaptador adaptador;
    private final ClienteMapper mapper;

    public ClienteControlador(ClienteServicio servicio, ClienteAdaptador adaptador, ClienteMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @Operation(summary = "Obtener un cliente por su ID", description = "Devuelve los detalles de un cliente específico basado en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> mostrar(
            @Parameter(description = "ID del cliente que se desea buscar", required = true) @PathVariable("id") Integer id) {
        try {
            Cliente cliente = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(cliente));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Modificar un cliente existente", description = "Permite actualizar los datos de un cliente basado en su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDto.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(
            @Parameter(description = "ID del cliente a modificar", required = true) @PathVariable Integer id,
            @RequestBody ClientePeticion peticion) {
        Cliente cliente = this.adaptador.peticionADireccion(id, peticion);
        this.servicio.actualizar(cliente);
        return ResponseEntity.ok(mapper.toDTO(cliente));
    }
}
