package com.banquito.cbs.aplicacion.producto.controlador;

import com.banquito.cbs.aplicacion.producto.controlador.adaptador.TarjetaAdaptador;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearTarjetaPeticion;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.servicio.TarjetaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tarjetas")
@CrossOrigin("*")
public class TarjetaControlador {

    private final TarjetaServicio servicio;
    private final TarjetaAdaptador adaptador;

    public TarjetaControlador(TarjetaServicio servicio, TarjetaAdaptador adaptador) {
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @Operation(summary = "Listar tarjetas de un cliente", description = "Devuelve todas las tarjetas asociadas a un cliente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjetas listadas exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> listar(
            @Parameter(description = "ID del cliente cuyas tarjetas se desean listar", required = true) 
            @RequestParam("idCliente") Integer idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.listarPorCliente(idCliente)));
    }

    @Operation(summary = "Obtener una tarjeta por su ID", description = "Devuelve los detalles de una tarjeta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta encontrada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(
            @Parameter(description = "ID de la tarjeta que se desea buscar", required = true) 
            @PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
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
