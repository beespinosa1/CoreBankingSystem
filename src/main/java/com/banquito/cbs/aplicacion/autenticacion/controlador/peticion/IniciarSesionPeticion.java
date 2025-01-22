package com.banquito.cbs.aplicacion.autenticacion.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class IniciarSesionPeticion
{
    @NotEmpty
    @NotBlank
    @NotNull
    private String usuario;

    @NotEmpty
    @NotBlank
    @NotNull
    private String contrasenia;

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
