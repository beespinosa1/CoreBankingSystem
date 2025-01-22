package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.ClienteAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.ClientePeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/clientes")
@CrossOrigin("*")
public class ClienteControlador
{
    private final ClienteServicio servicio;
    private final ClienteAdaptador adaptador;

    public ClienteControlador(ClienteServicio servicio, ClienteAdaptador adaptador) {
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable int id, @RequestBody ClientePeticion peticion)
    {
        Cliente cliente = this.adaptador.peticionADireccion(id, peticion);
        this.servicio.actualizar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(cliente));
    }
}
