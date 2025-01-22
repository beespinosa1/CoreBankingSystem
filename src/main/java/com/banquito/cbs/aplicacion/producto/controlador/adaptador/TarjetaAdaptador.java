package com.banquito.cbs.aplicacion.producto.controlador.adaptador;

import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearTarjetaPeticion;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;
import com.banquito.cbs.aplicacion.producto.servicio.TarjetaServicio;
import org.springframework.stereotype.Component;

@Component
public class TarjetaAdaptador {
    private final TarjetaServicio servicio;

    public TarjetaAdaptador(TarjetaServicio servicio) {
        this.servicio = servicio;
    }

    public Tarjeta peticionCreacionATarjeta(CrearTarjetaPeticion peticion) {
        Tarjeta tarjeta = new Tarjeta();

        this.llenarAtributos(tarjeta, peticion);

        return tarjeta;
    }

    private void llenarAtributos(Tarjeta tarjeta, CrearTarjetaPeticion peticion) {
        tarjeta.setClienteId(peticion.getClienteId());
        tarjeta.setCupoAprobado(peticion.getLimiteCredito());
        tarjeta.setCupoDisponible(peticion.getLimiteCredito());
        tarjeta.setDiaCorte(peticion.getCorte());
    }
}
