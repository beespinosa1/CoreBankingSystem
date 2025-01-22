package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.PersonaNaturalDTO;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaNaturalAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.PersonaNaturalMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaNaturalPeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaNaturalServicio;
//import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/personas-naturales")
@CrossOrigin("*")
public class PersonaNaturalControlador
{
    private final PersonaNaturalServicio servicio;
    private final PersonaNaturalAdaptador adaptador;
    private final PersonaNaturalMapper mapper;

    public PersonaNaturalControlador(PersonaNaturalServicio servicio, PersonaNaturalAdaptador adaptador, PersonaNaturalMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    /* @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.listar()));
    } */

    @GetMapping
    public ResponseEntity<List<PersonaNaturalDTO>> listar() {
        List<PersonaNatural> personas = this.servicio.listar();
        List<PersonaNaturalDTO> dtos = new ArrayList<>(personas.size());
        for(PersonaNatural persona : personas) {
            dtos.add(mapper.toDTO(persona));
        }
        return ResponseEntity.ok(dtos);
    }

    /* @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
    } */

    @GetMapping("/{id}")
    public ResponseEntity<PersonaNaturalDTO> mostrar(@PathVariable("id") Integer id) {
        try {
            PersonaNatural persona = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(persona));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PersonaNaturalPeticion campos) {
        PersonaNatural persona = this.adaptador.peticionAPersonaNatural(campos);
        servicio.crear(persona);
        //return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(persona));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaNaturalPeticion campos) {
        PersonaNatural persona = this.adaptador.peticionAPersonaNatural(id, campos);
        this.servicio.actualizar(persona);
        // return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
        return ResponseEntity.ok(mapper.toDTO(persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        this.servicio.eliminar(servicio.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
