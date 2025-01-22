package com.banquito.cbs.aplicacion.producto.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DepositoPeticion
{
    @NotBlank
    @NotEmpty
    @NotNull
    private String numeroCuenta;

    @NotBlank
    @NotEmpty
    @NotNull
    private BigDecimal valor;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
