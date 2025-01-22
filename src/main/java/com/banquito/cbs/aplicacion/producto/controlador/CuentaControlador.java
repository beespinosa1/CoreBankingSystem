package com.banquito.cbs.aplicacion.producto.controlador;

import com.banquito.cbs.aplicacion.producto.controlador.adaptador.CuentaAdaptador;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearCuentaPeticion;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.DepositoPeticion;
import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.servicio.CuentaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/cuentas")
@CrossOrigin("*")
public class CuentaControlador {
    private final CuentaServicio servicio;
    private final CuentaAdaptador adaptador;

    public CuentaControlador(CuentaServicio servicio, CuentaAdaptador adaptador) {
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam("idCliente") Integer idCliente)
    {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.listarPorCliente(idCliente)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CrearCuentaPeticion peticion) {
        Cuenta cuenta = this.adaptador.peticionCreacionACuenta(peticion);
        this.servicio.crearCuenta(cuenta, peticion.getDepositoInicial());

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(cuenta));
    }

    @PutMapping("/deposito")
    public ResponseEntity<?> depositar(@Valid @RequestBody DepositoPeticion peticion) {
        Cuenta cuenta = this.servicio.buscarPorNumero(peticion.getNumeroCuenta());
        this.servicio.depositarValores(cuenta, peticion.getValor());

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activarCuenta(@PathVariable("id") Integer id) {
        Cuenta cuenta = this.servicio.buscarPorId(id);
        this.servicio.activarCuenta(cuenta);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
    }

    @PutMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivarCuenta(@PathVariable("id") Integer id) {
        Cuenta cuenta = this.servicio.buscarPorId(id);
        this.servicio.inactivarCuenta(cuenta);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(cuenta));
    }
}
