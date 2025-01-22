package com.banquito.cbs.aplicacion.cliente.controlador.adaptador;

import com.banquito.cbs.aplicacion.cliente.controlador.peticion.ClientePeticion;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.cliente.servicio.ClienteServicio;
import org.springframework.stereotype.Component;

@Component
public class ClienteAdaptador
{
    private final ClienteServicio servicio;

    public ClienteAdaptador(ClienteServicio clienteServicio) {
        this.servicio = clienteServicio;
    }

    public Cliente peticionADireccion(ClientePeticion peticion)
    {
        Cliente cliente = new Cliente();

        this.llenarAtributos(cliente, peticion);

        return cliente;
    }

    public Cliente peticionADireccion(Integer id, ClientePeticion peticion)
    {
        Cliente cliente = this.servicio.buscarPorId(id);

        this.llenarAtributos(cliente, peticion);

        return cliente;
    }

    private void llenarAtributos(Cliente cliente, ClientePeticion peticion)
    {
        cliente.setPersonaNaturalId(peticion.getPersonaNaturalId());
        cliente.setPersonaJuridicaId(peticion.getPersonaJuridicaId());
        cliente.setTipo(peticion.getTipo());
        cliente.setIngresoPromedioMensual(peticion.getIngresoPromedioMensual());
        cliente.setEstado(peticion.getEstado());
    }
}
