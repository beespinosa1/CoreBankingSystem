package com.banquito.cbs.aplicacion.cliente.controlador.adaptador;

import com.banquito.cbs.aplicacion.cliente.controlador.peticion.PersonaJuridicaPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;
import com.banquito.cbs.aplicacion.cliente.servicio.PersonaJuridicaServicio;
import org.springframework.stereotype.Component;

@Component
public class PersonaJuridicaAdaptador
{
    private final PersonaJuridicaServicio servicio;

    public PersonaJuridicaAdaptador(PersonaJuridicaServicio servicio) {
        this.servicio = servicio;
    }

    public PersonaJuridica peticionAPersonaJuridica(PersonaJuridicaPeticion peticion)
    {
        PersonaJuridica persona = new PersonaJuridica();

        this.llenarAtributos(persona, peticion);

        return persona;
    }

    public PersonaJuridica peticionAPersonaJuridica(Integer id, PersonaJuridicaPeticion peticion)
    {
        PersonaJuridica persona = this.servicio.buscarPorId(id);

        this.llenarAtributos(persona, peticion);

        return persona;
    }

    private void llenarAtributos(PersonaJuridica persona, PersonaJuridicaPeticion peticion)
    {
        persona.setPersonaNaturalId(peticion.getPersonaNaturalId());
        persona.setRuc(peticion.getRuc());
        persona.setRazonSocial(peticion.getRazonSocial());
        persona.setNombreComercial(peticion.getNombreComercial());
        persona.setEmail(peticion.getEmail());
        persona.setNumeroTelefonico(peticion.getNumeroTelefonico());
        persona.setFechaConstitucion(peticion.getFechaConstitucion());
    }
}
