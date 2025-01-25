package com.banquito.cbs.aplicacion.transaccion.controlador.dto_org;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDTO {
    private Integer id;
    private Integer cuentaId;
    private Integer tarjetaId;
    @NotBlank(message = "El tipo de transacción es requerido")
    @Pattern(regexp = "DEP|RET|TRA|PAG", message = "El tipo debe ser DEP, RET, TRA o PAG")
    private String tipo;
    @NotBlank(message = "El canal es requerido")
    @Pattern(regexp = "WEB|MOV|ATM|VEN|OFI", message = "El canal debe ser WEB, MOV, ATM, VEN u OFI")
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
    @Pattern(regexp = "PEN|APR|REC|REV", message = "El estado debe ser PEN, APR, REC o REV")
    private String estado;
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
    private DetalleTransaccionDTO detalleTransaccion;
} 