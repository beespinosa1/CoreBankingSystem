package com.banquito.cbs.aplicacion.cliente.controlador.peticion;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DireccionPeticion
{
    private Integer personaNaturalId;

    private Integer personaJuridicaId;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String provincia;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String ciudad;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String canton;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String sector;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String callePrincipal;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String calleSecundaria;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String numero;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String referencia;

    private String detalle;

    public Integer getPersonaNaturalId() {
        return personaNaturalId;
    }

    public Integer getPersonaJuridicaId() {
        return personaJuridicaId;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCanton() {
        return canton;
    }

    public String getSector() {
        return sector;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public String getNumero() {
        return numero;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getDetalle() {
        return detalle;
    }
}
