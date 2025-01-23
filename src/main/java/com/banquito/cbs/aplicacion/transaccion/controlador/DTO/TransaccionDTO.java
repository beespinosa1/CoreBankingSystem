package com.banquito.cbs.aplicacion.transaccion.controlador.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransaccionDTO {
    private Integer id;
    private Integer cuentaId;
    private Integer tarjetaId;
    private String tipo;
    private String canal;
    private LocalDate fechaHora;
    private BigDecimal valor;
    private BigDecimal comision;
    private BigDecimal tazaInteres;
    private Boolean esDiferido;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private DetalleTransaccionDTO detalleTransaccion;
} 