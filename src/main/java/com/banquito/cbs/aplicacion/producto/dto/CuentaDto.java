package com.banquito.cbs.aplicacion.producto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {

    private Integer id;

    @NotNull(message = "El ID del cliente es requerido")
    private Integer clienteId;

    @NotBlank(message = "El tipo de cuenta es requerido")
    @Pattern(regexp = "AHO|COR", message = "El tipo de cuenta debe ser Ahorro (AHO) o Corriente (COR)")
    private String tipo;

    @NotBlank(message = "El número de cuenta es requerido")
    @Size(min = 8, max = 16, message = "El número de cuenta debe tener entre 8 y 16 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El número de cuenta debe contener solo números")
    private String numero;

    @NotNull(message = "El saldo total es requerido")
    @DecimalMin(value = "0.00", message = "El saldo total no puede ser negativo")
    @DecimalMax(value = "999999999.99", message = "El saldo total excede el límite permitido")
    private BigDecimal saldoTotal;

    @NotNull(message = "El saldo disponible es requerido")
    @DecimalMin(value = "0.00", message = "El saldo disponible no puede ser negativo")
    @DecimalMax(value = "999999999.99", message = "El saldo disponible excede el límite permitido")
    private BigDecimal saldoDisponible;

    @NotNull(message = "El saldo a acreditar es requerido")
    @DecimalMin(value = "0.00", message = "El saldo a acreditar no puede ser negativo")
    @DecimalMax(value = "999999999.99", message = "El saldo a acreditar excede el límite permitido")
    private BigDecimal saldoAcreditar;

    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ACT|INA", message = "El estado debe ser Activo (ACT) o Inactivo (INA)")
    private String estado;

    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;

    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
}