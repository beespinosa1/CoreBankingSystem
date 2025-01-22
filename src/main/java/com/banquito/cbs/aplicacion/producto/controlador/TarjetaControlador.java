package com.banquito.cbs.aplicacion.producto.controlador;

import com.banquito.cbs.aplicacion.producto.controlador.adaptador.TarjetaAdaptador;
import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearTarjetaPeticion;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.servicio.TarjetaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tarjetas")
@CrossOrigin("*")
public class TarjetaControlador {
    private final TarjetaServicio servicio;
    private final TarjetaAdaptador adaptador;

    public TarjetaControlador(TarjetaServicio servicio, TarjetaAdaptador adaptador) {
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam("idCliente") Integer idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.listarPorCliente(idCliente)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<?> almacenar(@RequestBody CrearTarjetaPeticion peticion) throws Exception {
        Tarjeta tarjeta = this.adaptador.peticionCreacionATarjeta(peticion);
        this.servicio.crearTarjeta(tarjeta);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(tarjeta));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable("id") Integer id) {
        Tarjeta tarjeta = this.servicio.buscarPorId(id);
        this.servicio.activarTarjeta(tarjeta);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(tarjeta));
    }

    @PutMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable("id") Integer id) {
        Tarjeta tarjeta = this.servicio.buscarPorId(id);
        this.servicio.inactivarTarjeta(tarjeta);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(tarjeta));
    }
}
