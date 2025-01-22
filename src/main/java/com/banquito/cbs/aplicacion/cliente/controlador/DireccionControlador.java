package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.DireccionAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.DireccionPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import com.banquito.cbs.aplicacion.cliente.servicio.DireccionServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/direcciones")
@CrossOrigin("*")
public class DireccionControlador
{
    private final ClienteServicio clienteServicio;
    private final DireccionServicio servicio;
    private final DireccionAdaptador adaptador;

    public DireccionControlador(DireccionServicio servicio, DireccionAdaptador adaptador, ClienteServicio clienteServicio)
    {
        this.clienteServicio = clienteServicio;
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam("idCliente") Integer idCliente)
    {
        Cliente cliente = clienteServicio.buscarPorId(idCliente);
        List<Direccion> direcciones = (cliente.getTipo().equals(ClienteServicio.CORPORATIVO))
                ? this.servicio.buscarPorPersonaJuridica(cliente.getPersonaJuridica())
                : this.servicio.buscarPorPersonaNatural(cliente.getPersonaNatural())
        ;

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(direcciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Integer id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody DireccionPeticion peticion)
    {
        Direccion direccion = this.adaptador.peticionADireccion(peticion);
        this.servicio.crear(direccion);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(direccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Integer id, @Valid @RequestBody DireccionPeticion peticion)
    {
        Direccion direccion = this.adaptador.peticionADireccion(id, peticion);
        this.servicio.actualizar(direccion);

        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id)
    {
        this.servicio.eliminar(servicio.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
