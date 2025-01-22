package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaJuridicaAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaJuridicaPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaJuridicaServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("personas-juridicas")
@CrossOrigin("*")
public class PersonaJuridicaControlador
{
    private final PersonaJuridicaServicio servicio;
    private final PersonaJuridicaAdaptador adaptador;

    public PersonaJuridicaControlador(PersonaJuridicaServicio servicio, PersonaJuridicaAdaptador adaptador) {
        this.servicio = servicio;
        this.adaptador = adaptador;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(UtilidadRespuesta.exito(this.servicio.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(campos);
        servicio.crear(persona);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaJuridicaPeticion campos) {
        PersonaJuridica persona = this.adaptador.peticionAPersonaJuridica(id, campos);
        this.servicio.actualizar(persona);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        this.servicio.eliminar(servicio.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
