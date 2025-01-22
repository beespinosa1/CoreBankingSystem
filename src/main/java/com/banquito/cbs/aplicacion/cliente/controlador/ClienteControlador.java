package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.ClienteDTO;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.ClienteAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.ClienteMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.ClientePeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/clientes")
@CrossOrigin("*")
public class ClienteControlador
{
    private final ClienteServicio servicio;
    private final ClienteAdaptador adaptador;
    private final ClienteMapper mapper;

    public ClienteControlador(ClienteServicio servicio, ClienteAdaptador adaptador, ClienteMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> mostrar(@PathVariable("id") Integer id) {
        try {
            Cliente cliente = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(cliente));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody ClientePeticion peticion)
    {
        Cliente cliente = this.adaptador.peticionADireccion(id, peticion);
        this.servicio.actualizar(cliente);
        // return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(cliente));
        return ResponseEntity.ok(mapper.toDTO(cliente));
    }
}
