package com.banquito.cbs.aplicacion.transaccion.controlador;

import com.banquito.cbs.aplicacion.transaccion.controlador.adaptador.TransaccionAdaptador;
import com.banquito.cbs.aplicacion.transaccion.controlador.DTO.TransaccionDTO;
import com.banquito.cbs.aplicacion.transaccion.controlador.mapper.TransaccionMapper;
import com.banquito.cbs.aplicacion.transaccion.controlador.peticion.ConsumoPeticion;
import com.banquito.cbs.aplicacion.transaccion.controlador.peticion.ConsumoValidacionPeticion;
import com.banquito.cbs.aplicacion.transaccion.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import com.banquito.cbs.aplicacion.transaccion.servicio.TransaccionServicio;
import com.banquito.cbs.compartido.excepciones.OperacionInvalidaExcepcion;
import com.banquito.cbs.compartido.utilidades.UtilidadObjeto;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/transacciones")
@CrossOrigin("*")
public class TransaccionControlador {
    private final TransaccionServicio servicio;
    private final TransaccionAdaptador adaptador;
    private final TransaccionMapper mapper;

    public TransaccionControlador(TransaccionServicio servicio, TransaccionAdaptador adaptador, TransaccionMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @GetMapping("/cuenta/{id}")
    public ResponseEntity<List<TransaccionDTO>> listarMovimientosCuenta(@PathVariable("id") Integer id) {
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

    @GetMapping("/tarjeta/{id}")
    public ResponseEntity<List<TransaccionDTO>> listarMovimientosTarjeta(@PathVariable("id") Integer id) {
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
    }

    /*@PostMapping("/deposito")
    public ResponseEntity<?> crearDeposito(@RequestBody Map<String, Object> datos) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/pago-tarjeta")
    public ResponseEntity<?> crearPagoTarjetaCredito(@RequestBody Map<String, Object> datos) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/transferencia-interna")
    public ResponseEntity<?> crearTransferenciaInterna(@RequestBody Map<String, Object> datos) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/transferencia-externa")
    public ResponseEntity<?> crearTransferenciaExterna(@RequestBody Map<String, Object> datos) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }*/

    @PostMapping("/consumo-tarjeta")
    public ResponseEntity<?> registrarConsumoTarjeta(@Valid @RequestBody ConsumoPeticion peticion) throws IllegalAccessException
    {
        Transaccion transaccion = this.adaptador.consumoATransaccion(peticion);
        this.servicio.registrarConsumoTarjeta(
                transaccion,
                peticion.getNumeroTarjeta(),
                peticion.getCvv(),
                peticion.getFechaCaducidad(),
                peticion.getDescripcion(),
                peticion.getNumeroCuenta(),
                peticion.getBeneficiario()
        );

        BigDecimal valorAcreditar = this.servicio.cobrarComisionesConsumo(transaccion, UtilidadObjeto.convertToMap(peticion.getDetalle()));
        this.servicio.enviarDineroBeneficiarioConsumo(transaccion, valorAcreditar);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Transaccion exitosa");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(respuesta));
    }

    @PostMapping("/consumo-tarjeta/validar")
    public ResponseEntity<?> validarConsumo(@Valid @RequestBody ConsumoValidacionPeticion peticion) throws IllegalAccessException
    {
        Transaccion transaccion = this.adaptador.validacionATransaccion(peticion);
        this.servicio.validarTransaccion(
                transaccion,
                peticion.getNumeroTarjeta(),
                peticion.getCvv(),
                peticion.getFechaCaducidad(),
                peticion.getDescripcion(),
                peticion.getNumeroCuenta(),
                peticion.getBeneficiario()
        );

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Transaccion valida");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(respuesta));
    }
}
