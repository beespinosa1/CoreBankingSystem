package com.banquito.cbs.aplicacion.producto.controlador;

import com.banquito.cbs.aplicacion.producto.controlador.adaptador.CuentaAdaptador;
import com.banquito.cbs.aplicacion.producto.controlador.dto.CuentaDto;
import com.banquito.cbs.aplicacion.producto.controlador.mapper.CuentaMapper;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearCuentaPeticion;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.DepositoPeticion;
import com.banquito.cbs.aplicacion.producto.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.servicio.CuentaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
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
@RequestMapping("v1/cuentas")
@CrossOrigin("*")
public class CuentaControlador {

        private final CuentaServicio servicio;
        private final CuentaAdaptador adaptador;
        private final CuentaMapper mapper;

        public CuentaControlador(CuentaServicio servicio, CuentaAdaptador adaptador, CuentaMapper mapper) {
                this.servicio = servicio;
                this.adaptador = adaptador;
                this.mapper = mapper;
        }

        @Operation(summary = "Listar cuentas de un cliente", description = "Devuelve todas las cuentas asociadas a un cliente específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cuentas listadas exitosamente", content = @Content(mediaType = "application/json"))
        })
        @GetMapping
        public ResponseEntity<List<CuentaDto>> listar(
                        @Parameter(description = "ID del cliente cuyas cuentas se desean listar", required = true) @RequestParam("idCliente") Integer idCliente) {
                try {
                        List<Cuenta> cuentas = this.servicio.listarPorCliente(idCliente);
                        List<CuentaDto> dtos = new ArrayList<>(cuentas.size());
                        for (Cuenta cuenta : cuentas) {
                                dtos.add(mapper.toDto(cuenta));
                        }
                        return ResponseEntity.ok(dtos);
                } catch (NotFoundException nfe) {
                        System.err.println(nfe.getMessage());
                        return ResponseEntity.notFound().build();
                }
        }

        @Operation(summary = "Obtener una cuenta por su ID", description = "Devuelve los detalles de una cuenta específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cuenta encontrada exitosamente", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<CuentaDto> mostrar(
                        @Parameter(description = "ID de la cuenta que se desea buscar", required = true) @PathVariable("id") Integer id) {
                try {
                        Cuenta cuenta = this.servicio.buscarPorId(id);
                        return ResponseEntity.ok(mapper.toDto(cuenta));
                } catch (NotFoundException nfe) {
                        System.err.println(nfe.getMessage());
                        return ResponseEntity.notFound().build();
                }
        }

        @Operation(summary = "Crear una nueva cuenta", description = "Permite registrar una nueva cuenta bancaria para un cliente.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "400", description = "Datos de la solicitud inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<?> crear(
                        @Valid @RequestBody CrearCuentaPeticion peticion) {
                Cuenta cuenta = this.adaptador.peticionCreacionACuenta(peticion);
                this.servicio.crearCuenta(cuenta, peticion.getDepositoInicial());
                return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(cuenta));
        }

        @Operation(summary = "Realizar un depósito en una cuenta", description = "Permite realizar un depósito en una cuenta específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Depósito realizado exitosamente", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
        })
        @PutMapping("/deposito")
        public ResponseEntity<?> depositar(
                        @Valid @RequestBody DepositoPeticion peticion) {
                Cuenta cuenta = this.servicio.buscarPorNumero(peticion.getNumeroCuenta());
                this.servicio.depositarValores(cuenta, peticion.getValor());
                return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
        }

        @Operation(summary = "Activar una cuenta", description = "Permite activar una cuenta específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
        })
        @PutMapping("/{id}/activar")
        public ResponseEntity<?> activarCuenta(
                        @Parameter(description = "ID de la cuenta que se desea activar", required = true) @PathVariable("id") Integer id) {
                Cuenta cuenta = this.servicio.buscarPorId(id);
                this.servicio.activarCuenta(cuenta);
                return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
        }

        @Operation(summary = "Inactivar una cuenta", description = "Permite inactivar una cuenta específica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cuenta inactivada exitosamente", content = @Content(mediaType = "application/json")),
                        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
        })
        @PutMapping("/{id}/inactivar")
        public ResponseEntity<?> inactivarCuenta(
                        @Parameter(description = "ID de la cuenta que se desea inactivar", required = true) @PathVariable("id") Integer id) {
                Cuenta cuenta = this.servicio.buscarPorId(id);
                this.servicio.inactivarCuenta(cuenta);
                return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
        }
}
