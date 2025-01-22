package com.banquito.cbs.aplicacion.autenticacion.controlador.peticion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UsuarioValidacionPeticion
{
    @NotEmpty
    @NotNull
    @NotBlank
    private String usuario;

    public String getUsuario() {
        return usuario;
    }
}
