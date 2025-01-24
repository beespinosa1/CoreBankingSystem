package com.banquito.cbs.aplicacion.producto.controlador;

import com.banquito.cbs.aplicacion.producto.controlador.adaptador.TarjetaAdaptador;
import com.banquito.cbs.aplicacion.producto.controlador.DTO.TarjetaDTO;
import com.banquito.cbs.aplicacion.producto.controlador.mapper.TarjetaMapper;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearTarjetaPeticion;
import com.banquito.cbs.aplicacion.producto.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.servicio.TarjetaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/tarjetas")
@CrossOrigin("*")
public class TarjetaControlador {

    private final TarjetaServicio servicio;
    private final TarjetaAdaptador adaptador;
    private final TarjetaMapper mapper;

    public TarjetaControlador(TarjetaServicio servicio, TarjetaAdaptador adaptador, TarjetaMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar tarjetas de un cliente", description = "Devuelve todas las tarjetas asociadas a un cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjetas listadas exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TarjetaDTO>> listar(
            @Parameter(description = "ID del cliente cuyas tarjetas se desean listar", required = true) 
            @RequestParam("idCliente") Integer idCliente) {
        try {
            List<Tarjeta> tarjetas = this.servicio.listarPorCliente(idCliente);
            List<TarjetaDTO> dtos = new ArrayList<>(tarjetas.size());
            for(Tarjeta tarjeta : tarjetas) {
                dtos.add(mapper.toDTO(tarjeta));
            }
            return ResponseEntity.ok(dtos);
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener una tarjeta por su ID", description = "Devuelve los detalles de una tarjeta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta encontrada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarjetaDTO> mostrar(
            @Parameter(description = "ID de la tarjeta que se desea buscar", required = true) 
            @PathVariable("id") Integer id) {
        try {
            Tarjeta tarjeta = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(mapper.toDTO(tarjeta));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva tarjeta", description = "Permite registrar una nueva tarjeta para un cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarjeta creada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> almacenar(
            @Parameter(description = "Datos necesarios para crear la tarjeta", required = true) 
            @RequestBody CrearTarjetaPeticion peticion) throws Exception {
        Tarjeta tarjeta = this.adaptador.peticionCreacionATarjeta(peticion);
        this.servicio.crearTarjeta(tarjeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(tarjeta));
    }

    @Operation(summary = "Activar una tarjeta", description = "Permite activar una tarjeta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta activada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)
    })
    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activar(
            @Parameter(description = "ID de la tarjeta que se desea activar", required = true) 
            @PathVariable("id") Integer id) {
            Tarjeta tarjeta = this.servicio.buscarPorId(id);
            this.servicio.activarTarjeta(tarjeta);
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(tarjeta));
    }

    @Operation(summary = "Inactivar una tarjeta", description = "Permite inactivar una tarjeta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta inactivada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)
    })
    @PutMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(
            @Parameter(description = "ID de la tarjeta que se desea inactivar", required = true) 
            @PathVariable("id") Integer id) {
            Tarjeta tarjeta = this.servicio.buscarPorId(id);
            this.servicio.inactivarTarjeta(tarjeta);
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(tarjeta));
    }
}
