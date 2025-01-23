package com.banquito.cbs.aplicacion.transaccion.controlador.DTO;

import java.time.LocalDate;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalleTransaccionDTO {
    private Integer transaccionId;
    private String tipoCuenta;
    private String beneficiario;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String binBancoOrigen;
    private String binBancoDestino;
    private String descripcion;
    private String detalleJson;
    private LocalDate fechaAutorizacion;
    private Map<String, Object> detalleMap;
} 