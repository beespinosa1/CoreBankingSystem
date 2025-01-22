package com.banquito.cbs.aplicacion.cliente.controlador.adaptador;

import com.banquito.cbs.aplicacion.cliente.controlador.peticion.DireccionPeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;
import com.banquito.cbs.aplicacion.cliente.servicio.DireccionServicio;
import org.springframework.stereotype.Component;

@Component
public class DireccionAdaptador
{
    private final DireccionServicio servicio;

    public DireccionAdaptador(DireccionServicio direccionServicio) {
        this.servicio = direccionServicio;
    }

    public Direccion peticionADireccion(DireccionPeticion peticion)
    {
        Direccion direccion = new Direccion();

        this.llenarAtributos(direccion, peticion);

        return direccion;
    }

    public Direccion peticionADireccion(Integer id, DireccionPeticion peticion)
    {
        Direccion direccion = this.servicio.buscarPorId(id);

        this.llenarAtributos(direccion, peticion);

        return direccion;
    }

    private void llenarAtributos(Direccion direccion, DireccionPeticion peticion)
    {
        direccion.setPersonaNaturalId(peticion.getPersonaNaturalId());
        direccion.setPersonaJuridicaId(peticion.getPersonaJuridicaId());
        direccion.setProvincia(peticion.getProvincia());
        direccion.setCiudad(peticion.getCiudad());
        direccion.setCanton(peticion.getCanton());
        direccion.setSector(peticion.getSector());
        direccion.setCallePrincipal(peticion.getCallePrincipal());
        direccion.setCalleSecundaria(peticion.getCalleSecundaria());
        direccion.setNumero(peticion.getNumero());
        direccion.setReferencia(peticion.getReferencia());
        direccion.setDetalle(peticion.getDetalle());
    }
}
