package com.banquito.cbs.aplicacion.transaccion.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemDetalleTransaccionDto {
    private String referencia;
    private BigDecimal comision;
    private String numeroCuenta;
}
