package com.banquito.cbs.aplicacion.producto.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CrearCuentaPeticion
{
    @NotBlank
    @NotEmpty
    @NotNull
    private Integer clienteId;

    @NotBlank
    @NotEmpty
    @NotNull
    private String tipo;

    @NotBlank
    @NotEmpty
    @NotNull
    private BigDecimal depositoInicial;

    public Integer getClienteId() {
        return clienteId;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getDepositoInicial() {
        return depositoInicial;
    }
}
