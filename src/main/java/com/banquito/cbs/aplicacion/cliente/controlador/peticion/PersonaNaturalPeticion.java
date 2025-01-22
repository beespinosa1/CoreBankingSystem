package com.banquito.cbs.aplicacion.cliente.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PersonaNaturalPeticion {
    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String tipoIdentificacion;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String identificacion;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String primerNombre;

    private String segundoNombre;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String primerApellido;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String segundoApellido;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String email;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private String numeroTelefonico;

    @NotEmpty
    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    private LocalDate fechaNacimiento;

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getEmail() {
        return email;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
}
