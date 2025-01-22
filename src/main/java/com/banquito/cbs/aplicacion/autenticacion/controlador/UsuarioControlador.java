package com.banquito.cbs.aplicacion.autenticacion.controlador;

import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;
import com.banquito.cbs.aplicacion.autenticacion.service.UsuarioServicio;
import com.banquito.cbs.compartido.utilidades.UtilidadRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("usuarios")
@CrossOrigin("*")
public class UsuarioControlador
{
    private final UsuarioServicio servicio;

    public UsuarioControlador(UsuarioServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<?> listar()
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable("id") Integer id)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(this.servicio.find(id)));
    }

    @PostMapping
    public ResponseEntity<?> almacenar(@Valid @RequestBody Usuario usuario)
    {
        this.servicio.crear(usuario);

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario registrado exitosamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(datos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable("id") Integer id)
    {
        Usuario usuarioActual = this.servicio.find(id);
        this.servicio.editar(usuarioActual, usuario.getContrasenia());

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario actualizado exitosamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id)
    {
        this.servicio.eliminar(this.servicio.find(id));

        Map<String, Object> datos = new HashMap<>();
        datos.put("mensaje", "Usuario eliminado exitosamente");

        return ResponseEntity.status(HttpStatus.CREATED).body(UtilidadRespuesta.exito(datos));
    }
}
