package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.PersonaJuridicaDTO;
import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaJuridicaAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.mapper.PersonaJuridicaMapper;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaJuridicaPeticion;
import com.banquito.cbs.aplicacion.cliente.excepcion.NotFoundException;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaJuridicaServicio;
//import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/personas-juridicas")
@CrossOrigin("*")
public class PersonaJuridicaControlador
{
    private final PersonaJuridicaServicio servicio;
    private final PersonaJuridicaAdaptador adaptador;
    private final PersonaJuridicaMapper mapper;

    public PersonaJuridicaControlador(PersonaJuridicaServicio servicio, PersonaJuridicaAdaptador adaptador, PersonaJuridicaMapper mapper) {
        this.servicio = servicio;
        this.adaptador = adaptador;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PersonaJuridicaDTO>> listar() {
        List<PersonaJuridica> personas = this.servicio.listar();
        List<PersonaJuridicaDTO> dtos = new ArrayList<>(personas.size());
        for(PersonaJuridica persona : personas) {
            dtos.add(mapper.toDTO(persona));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaJuridicaDTO> mostrar(@PathVariable("id") Integer id) {
        try {
            PersonaJuridica persona = this.servicio.buscarPorId(id);
            return ResponseEntity.ok(this.mapper.toDTO(persona));
        } catch (NotFoundException nfe) {
            System.err.println(nfe.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(campos);
        servicio.crear(persona);
        //return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(persona));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(id, campos);
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
