package com.banquito.cbs.aplicacion.producto.dto;

import lombok.Data;

@Data
public class CrearTarjetaDto {
    private String identificacion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String tipo;
    private String franquicia;
}
