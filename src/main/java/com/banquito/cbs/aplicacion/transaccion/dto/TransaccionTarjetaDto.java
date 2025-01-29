package com.banquito.cbs.aplicacion.transaccion.dto;
import lombok.Data;

@Data
public class TransaccionTarjetaDto {
    private String numeroTarjeta;
    private String valor;
    private String descripcion;
    private String numeroCuenta;
    private Boolean esDiferido;
    private Boolean tieneIntereses;
    private Integer cuotas;
    private DetalleTransaccionDto detalle;
}