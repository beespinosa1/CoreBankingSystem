package com.banquito.cbs.aplicacion.producto.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class TarjetaMarcaDto {
    private String numero;
    private String fechaCaducidad;
    private String cvv;
    private String tipo;
    private String franquicia;
    private LocalDateTime fechaEmision;
}
