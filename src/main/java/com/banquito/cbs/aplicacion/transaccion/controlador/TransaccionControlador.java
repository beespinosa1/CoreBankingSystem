package com.banquito.cbs.aplicacion.transaccion.controlador;

import com.banquito.cbs.aplicacion.transaccion.controlador.mapper.TransaccionMapper;
import com.banquito.cbs.aplicacion.transaccion.dto.TransaccionDto;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import com.banquito.cbs.aplicacion.transaccion.servicio.TransaccionServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadObjeto;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /*@Operation(summary = "Listar movimientos de una cuenta", description = "Devuelve todos los movimientos asociados a una cuenta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos listados exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    })
    @GetMapping("/cuenta/{id}")
    public ResponseEntity<List<TransaccionDTO>> listarMovimientosCuenta(@Parameter(description = "ID de la cuenta cuyos movimientos se desean listar", required = true)
                                                                        @PathVariable("id") Integer id) {
        try {
            List<Transaccion> transacciones = this.servicio.listarPorCuenta(id);
            List<TransaccionDTO> dtos = new ArrayList<>(transacciones.size());
            for(Transaccion transaccion : transacciones) {
                dtos.add(mapper.toDTO(transaccion));
            }
            return ResponseEntity.ok(dtos);
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar movimientos de una tarjeta", description = "Devuelve todos los movimientos asociados a una tarjeta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos listados exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)
    })
    @GetMapping("/tarjeta/{id}")
    public ResponseEntity<List<TransaccionDTO>> listarMovimientosTarjeta(@Parameter(description = "ID de la tarjeta cuyos movimientos se desean listar", required = true) 
                                                                         @PathVariable("id") Integer id) {
        try {
            List<Transaccion> transacciones = this.servicio.listarPorTarjeta(id);
            List<TransaccionDTO> dtos = new ArrayList<>(transacciones.size());
            for(Transaccion transaccion : transacciones) {
                dtos.add(mapper.toDTO(transaccion));
            }
            return ResponseEntity.ok(dtos);
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }*/

    @Operation(summary = "Registrar consumo con tarjeta", description = "Permite registrar un consumo con tarjeta de crédito o débito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumo registrado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
    })
    @PostMapping("/consumo-tarjeta")
    public ResponseEntity<?> registrarConsumoTarjeta(
            @Parameter(description = "Detalles del consumo a registrar", required = true) 
            @Valid @RequestBody TransaccionDto peticion) throws IllegalAccessException {
        Transaccion transaccion = this.mapper.toPersistence(peticion);

        this.servicio.registrarConsumoTarjeta(
                transaccion,
                peticion.getNumeroTarjeta(),
                peticion.getDescripcion(),
                peticion.getNumeroCuenta(),
                peticion.getTieneIntereses(),
                peticion.getCuotas()
        );

        BigDecimal valorAcreditar = this.servicio.cobrarComisionesConsumo(transaccion, UtilidadObjeto.convertToMap(peticion.getDetalle()));
        this.servicio.enviarDineroBeneficiarioConsumo(transaccion, valorAcreditar);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Transacción exitosa");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(respuesta));
    }
}
