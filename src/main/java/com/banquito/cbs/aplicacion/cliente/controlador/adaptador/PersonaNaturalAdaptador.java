package com.banquito.cbs.aplicacion.cliente.controlador.adaptador;

import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaNaturalPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaNaturalServicio;
import org.springframework.stereotype.Component;

@Component
public class PersonaNaturalAdaptador {
    private final PersonaNaturalServicio servicio;

    public PersonaNaturalAdaptador(PersonaNaturalServicio servicio) {
        this.servicio = servicio;
    }

    public PersonaNatural peticionAPersonaNatural(PersonaNaturalPeticion peticion)
    {
        PersonaNatural persona = new PersonaNatural();

        this.llenarAtributos(persona, peticion);

        return persona;
    }

    public PersonaNatural peticionAPersonaNatural(Integer id, PersonaNaturalPeticion peticion)
    {
        PersonaNatural persona = this.servicio.buscarPorId(id);

        this.llenarAtributos(persona, peticion);

        return persona;
    }

    private void llenarAtributos(PersonaNatural persona, PersonaNaturalPeticion peticion)
    {
        persona.setTipoIdentificacion(peticion.getTipoIdentificacion());
        persona.setIdentificacion(peticion.getIdentificacion());
        persona.setPrimerNombre(peticion.getPrimerNombre());
        persona.setSegundoNombre(peticion.getSegundoNombre());
        persona.setPrimerApellido(peticion.getPrimerApellido());
        persona.setSegundoApellido(peticion.getSegundoApellido());
        persona.setEmail(peticion.getEmail());
        persona.setNumeroTelefonico(peticion.getNumeroTelefonico());
        persona.setFechaNacimiento(peticion.getFechaNacimiento());
    }
}
