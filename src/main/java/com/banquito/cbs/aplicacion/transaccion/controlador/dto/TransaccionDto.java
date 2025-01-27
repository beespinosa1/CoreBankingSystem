package com.banquito.cbs.aplicacion.transaccion.controlador.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDto {
    private Integer id;
    private Integer cuentaId;
    private Integer tarjetaId;
    @NotBlank(message = "El tipo de transacción es requerido")
    @Pattern(regexp = "DEP|PTC|CNS|REV|TRI|TRE|PAC", 
            message = "El tipo debe ser Depósito (DEP), Pago Tarjeta Crédito (PTC), Consumo (CNS), Reverso (REV), Transferencia Interna (TRI), Transferencia Externa (TRE) o Pago Automático (PAC)")
    private String tipo;
    @NotBlank(message = "El canal es requerido")
    @Pattern(regexp = "WEB|EXT|POS", 
            message = "El canal debe ser Web (WEB), Externo (EXT) o Punto de Venta (POS)")
    private String canal;
    @NotNull(message = "La fecha y hora es requerida")
    @PastOrPresent(message = "La fecha y hora no puede ser futura")
    private LocalDate fechaHora;
    @NotNull(message = "El valor es requerido")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    @DecimalMax(value = "999999999.99", message = "El valor excede el límite permitido")
    private BigDecimal valor;
    @DecimalMin(value = "0.00", message = "La comisión no puede ser negativa")
    @DecimalMax(value = "999999.99", message = "La comisión excede el límite permitido")
    private BigDecimal comision;
    @DecimalMin(value = "0.00", message = "La tasa de interés no puede ser negativa")
    @DecimalMax(value = "100.00", message = "La tasa de interés no puede ser mayor a 100%")
    private BigDecimal tazaInteres;
    private Boolean esDiferido;
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ANU|APR|REC|REV|PEN", 
            message = "El estado debe ser Anulado (ANU), Aprobado (APR), Rechazado (REC), Reversado (REV) o Pendiente (PEN)")
    private String estado;
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
    private DetalleTransaccionDto detalleTransaccion;
}