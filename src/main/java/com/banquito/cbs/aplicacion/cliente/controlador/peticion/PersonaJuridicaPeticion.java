package com.banquito.cbs.aplicacion.cliente.controlador.peticion;

import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PersonaJuridicaPeticion {
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private Integer personaNaturalId;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String ruc;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String razonSocial;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String nombreComercial;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String email;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String numeroTelefonico;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private LocalDate fechaConstitucion;

    public Integer getPersonaNaturalId() {
        return personaNaturalId;
    }

    public String getRuc() {
        return ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getEmail() {
        return email;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public LocalDate getFechaConstitucion() {
        return fechaConstitucion;
    }
}
