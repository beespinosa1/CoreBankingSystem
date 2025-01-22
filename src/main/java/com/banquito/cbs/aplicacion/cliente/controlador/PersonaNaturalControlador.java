package com.banquito.cbs.aplicacion.cliente.controlador;

import com.banquito.cbs.aplicacion.cliente.controlador.adaptador.PersonaNaturalAdaptador;
import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaNaturalPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaNaturalServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("personas-naturales")
@CrossOrigin("*")
public class PersonaNaturalControlador
{
    private final PersonaNaturalServicio servicio;
    private final PersonaNaturalAdaptador adaptador;

    public PersonaNaturalControlador(PersonaNaturalServicio servicio, PersonaNaturalAdaptador adaptador) {
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
    public ResponseEntity<?> crear(@Valid @RequestBody PersonaNaturalPeticion campos) {
        PersonaNatural persona = this.adaptador.peticionAPersonaNatural(campos);
        servicio.crear(persona);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @Valid @RequestBody PersonaNaturalPeticion campos) {
        PersonaNatural persona = this.adaptador.peticionAPersonaNatural(id, campos);
        this.servicio.actualizar(persona);

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        this.servicio.eliminar(servicio.buscarPorId(id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
