package com.banquito.cbs.aplicacion.cliente.controlador.peticion;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ClientePeticion {
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private Integer personaNaturalId;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private Integer personaJuridicaId;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String tipo;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private BigDecimal ingresoPromedioMensual;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String estado;

    public Integer getPersonaNaturalId() {
        return personaNaturalId;
    }

    public Integer getPersonaJuridicaId() {
        return personaJuridicaId;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getIngresoPromedioMensual() {
        return ingresoPromedioMensual;
    }

    public String getEstado() {
        return estado;
    }
}
