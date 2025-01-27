package com.banquito.cbs.aplicacion.transaccion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaTransaccionDto {
    private String mensaje;
    private String estado;
    private LocalDateTime fechaTransaccion;
    private BigDecimal valorTransaccion;
    private String numeroReferencia;
} 