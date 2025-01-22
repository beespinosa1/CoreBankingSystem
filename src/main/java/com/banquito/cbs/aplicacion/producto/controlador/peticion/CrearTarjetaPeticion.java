package com.banquito.cbs.aplicacion.producto.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CrearTarjetaPeticion
{
    @NotBlank
    @NotEmpty
    @NotNull
    private Integer clienteId;

    @NotBlank
    @NotEmpty
    @NotNull
    private BigDecimal limiteCredito;

    @NotBlank
    @NotEmpty
    @NotNull
    private Integer corte;

    public Integer getClienteId() {
        return clienteId;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public Integer getCorte() {
        return corte;
    }
}
