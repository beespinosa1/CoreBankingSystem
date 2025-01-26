package com.banquito.cbs.aplicacion.transaccion.dto;

import lombok.Data;

@Data
public class DetalleTransaccionDto {
    private ItemDetalleTransaccionDto gtw;
    private ItemDetalleTransaccionDto processor;
    private ItemDetalleTransaccionDto marca;
}
