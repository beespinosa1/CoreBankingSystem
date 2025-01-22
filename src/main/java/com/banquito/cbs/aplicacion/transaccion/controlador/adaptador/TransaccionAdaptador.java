package com.banquito.cbs.aplicacion.transaccion.controlador.adaptador;

import com.banquito.cbs.aplicacion.transaccion.controlador.peticion.ConsumoPeticion;
import com.banquito.cbs.aplicacion.transaccion.controlador.peticion.ConsumoValidacionPeticion;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import com.banquito.cbs.aplicacion.transaccion.servicio.TransaccionServicio;
import org.springframework.stereotype.Component;

@Component
public class TransaccionAdaptador
{
    private final TransaccionServicio servicio;

    public TransaccionAdaptador(TransaccionServicio servicio) {
        this.servicio = servicio;
    }

    public Transaccion consumoATransaccion(ConsumoPeticion peticion)
    {
        Transaccion transaccion = new Transaccion();

        transaccion.setCanal("EXT");
        transaccion.setValor(peticion.getValor());
        transaccion.setEsDiferido(peticion.getEsDiferido());

        return transaccion;
    }

    public Transaccion validacionATransaccion(ConsumoValidacionPeticion peticion)
    {
        Transaccion transaccion = new Transaccion();

        transaccion.setCanal("EXT");
        transaccion.setValor(peticion.getValor());
        transaccion.setEsDiferido(peticion.getEsDiferido());

        return transaccion;
    }
}
