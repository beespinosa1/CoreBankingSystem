package com.banquito.cbs.aplicacion.transaccion.controlador;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.cbs.aplicacion.transaccion.controlador.mapper.TransaccionMapper;
import com.banquito.cbs.aplicacion.transaccion.dto.RespuestaTransaccionDto;
import com.banquito.cbs.aplicacion.transaccion.dto.TransaccionDto;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import com.banquito.cbs.aplicacion.transaccion.servicio.TransaccionServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadObjeto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/transacciones")
@CrossOrigin("*")
@Slf4j
public class TransaccionControlador {

    private final TransaccionServicio servicio;
    private final TransaccionMapper mapper;

    public TransaccionControlador(TransaccionServicio servicio, TransaccionMapper mapper) {
        this.servicio = servicio;
        this.mapper = mapper;
    }

    /*
     * @Operation(summary = "Listar movimientos de una cuenta", description =
     * "Devuelve todos los movimientos asociados a una cuenta específica.")
     * 
     * @ApiResponses(value = {
     * 
     * @ApiResponse(responseCode = "200", description =
     * "Movimientos listados exitosamente",
     * content = @Content(mediaType = "application/json")),
     * 
     * @ApiResponse(responseCode = "404", description = "Cuenta no encontrada",
     * content = @Content)
     * })
     * 
     * @GetMapping("/cuenta/{id}")
     * public ResponseEntity<List<TransaccionDto>>
     * listarMovimientosCuenta(@Parameter(description =
     * "ID de la cuenta cuyos movimientos se desean listar", required = true)
     * 
     * @PathVariable("id") Integer id) {
     * try {
     * List<Transaccion> transacciones = this.servicio.listarPorCuenta(id);
     * List<TransaccionDto> dtos = new ArrayList<>(transacciones.size());
     * for(Transaccion transaccion : transacciones) {
     * dtos.add(mapper.toDto(transaccion));
     * }
     * return ResponseEntity.ok(dtos);
     * } catch (NotFoundException nfe) {
     * log.error("Cuenta con ID {} no encontrada", id, nfe);
     * return ResponseEntity.notFound().build();
     * }
     * }
     * 
     * @Operation(summary = "Listar movimientos de una tarjeta", description =
     * "Devuelve todos los movimientos asociados a una tarjeta específica.")
     * 
     * @ApiResponses(value = {
     * 
     * @ApiResponse(responseCode = "200", description =
     * "Movimientos listados exitosamente",
     * content = @Content(mediaType = "application/json")),
     * 
     * @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada",
     * content = @Content)
     * })
     * 
     * @GetMapping("/tarjeta/{id}")
     * public ResponseEntity<List<TransaccionDto>>
     * listarMovimientosTarjeta(@Parameter(description =
     * "ID de la tarjeta cuyos movimientos se desean listar", required = true)
     * 
     * @PathVariable("id") Integer id) {
     * try {
     * List<Transaccion> transacciones = this.servicio.listarPorTarjeta(id);
     * List<TransaccionDto> dtos = new ArrayList<>(transacciones.size());
     * for(Transaccion transaccion : transacciones) {
     * dtos.add(mapper.toDto(transaccion));
     * }
     * return ResponseEntity.ok(dtos);
     * } catch (NotFoundException nfe) {
     * log.error("Tarjeta con ID {} no encontrada", id, nfe);
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    @Operation(summary = "Registrar consumo con tarjeta", description = "Permite registrar un consumo con tarjeta de crédito o débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo registrado exitosamente", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping("/consumo-tarjeta")
    public ResponseEntity<RespuestaTransaccionDto> registrarConsumoTarjeta(
            @Parameter(description = "Detalles del consumo a registrar", required = true) @Valid @RequestBody TransaccionDto peticion)
            throws IllegalAccessException {
        Transaccion transaccion = this.mapper.toPersistence(peticion);

        this.servicio.registrarConsumoTarjeta(
                transaccion,
                peticion.getNumeroTarjeta(),
                peticion.getDescripcion(),
                peticion.getNumeroCuenta(),
                peticion.getTieneIntereses(),
                peticion.getCuotas());

        BigDecimal valorAcreditar = this.servicio.cobrarComisionesConsumo(transaccion,
                UtilidadObjeto.convertToMap(peticion.getDetalle()));
        this.servicio.enviarDineroBeneficiarioConsumo(transaccion, valorAcreditar);

        RespuestaTransaccionDto respuesta = new RespuestaTransaccionDto();
        respuesta.setMensaje("Transacción exitosa");
        respuesta.setEstado("COMPLETADA");
        respuesta.setFechaTransaccion(transaccion.getFechaCreacion());
        respuesta.setValorTransaccion(transaccion.getValor());
        respuesta.setNumeroReferencia(transaccion.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
