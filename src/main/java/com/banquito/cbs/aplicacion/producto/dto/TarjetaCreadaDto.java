package com.banquito.cbs.aplicacion.producto.dto;

import lombok.Data;

@Data
public class TarjetaCreadaDto {
    private String numero;
    private String cvv;
    private String fechaCaducidad;
}
