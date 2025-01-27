package com.banquito.cbs.aplicacion.transaccion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.banquito.cbs.aplicacion.transaccion.controlador.dto.DetalleTransaccionDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransaccionDto {

    private Integer id;

    @NotNull(message = "El valor es requerido")
    private BigDecimal valor;

    @NotBlank(message = "El tipo es requerido")
    @Pattern(regexp = "DEP|RET|TRA|PAG", 
            message = "El tipo debe ser Depósito (DEP), Retiro (RET), Transferencia (TRA) o Pago (PAG)")
    private String tipo;

    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "PEN|COM|REV|ANU", 
            message = "El estado debe ser Pendiente (PEN), Completado (COM), Reversado (REV) o Anulado (ANU)")
    private String estado;

    private LocalDateTime fechaHora;

    @Size(max = 128, message = "La descripción no puede exceder los 128 caracteres")
    private String descripcion;

    @Size(max = 20, message = "El número de cuenta no puede exceder los 20 caracteres")
    private String numeroCuenta;

    @Size(max = 20, message = "El número de tarjeta no puede exceder los 20 caracteres")
    private String numeroTarjeta;

    private Boolean tieneIntereses;

    private Integer cuotas;

    private Map<String, Object> detalle;

    private DetalleTransaccionDto detalleTransaccion;
}
