package com.banquito.cbs.aplicacion.producto.controlador.adaptador;

import com.banquito.cbs.aplicacion.producto.controlador.peticion.CrearCuentaPeticion;
import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;
import com.banquito.cbs.aplicacion.producto.servicio.CuentaServicio;
import org.springframework.stereotype.Component;

@Component
public class CuentaAdaptador {
    private final CuentaServicio servicio;

    public CuentaAdaptador(CuentaServicio servicio) {
        this.servicio = servicio;
    }

    public Cuenta peticionCreacionACuenta(CrearCuentaPeticion peticion) {
        Cuenta cuenta = new Cuenta();

        this.llenarAtributos(cuenta, peticion);

        return cuenta;
    }

    private void llenarAtributos(Cuenta cuenta, CrearCuentaPeticion peticion)
    {
        cuenta.setClienteId(peticion.getClienteId());
        cuenta.setTipo(peticion.getTipo());
    }
}
