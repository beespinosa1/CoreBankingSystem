package com.banquito.cbs.aplicacion.producto.controlador.dto;

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
public class TarjetaDTO {
    
    private Integer id;
    
    @NotNull(message = "El ID del cliente es requerido")
    private Integer clienteId;
    
    @NotBlank(message = "El número de tarjeta es requerido")
    @Size(min = 16, max = 16, message = "El número de tarjeta debe tener 16 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El número de tarjeta debe contener solo números")
    private String numero;
    
    private String clave;
    
    @NotNull(message = "La fecha de expiración es requerida")
    @Future(message = "La fecha de expiración debe ser futura")
    private LocalDate fechaExpiracion;
    
    @NotBlank(message = "El CVV es requerido")
    @Size(min = 3, max = 4, message = "El CVV debe tener entre 3 y 4 dígitos")
    private String cvv;
    
    @NotNull(message = "El cupo aprobado es requerido")
    @DecimalMin(value = "0.01", message = "El cupo aprobado debe ser mayor a 0")
    @DecimalMax(value = "999999999.99", message = "El cupo aprobado excede el límite permitido")
    private BigDecimal cupoAprobado;
    
    @NotNull(message = "El cupo disponible es requerido")
    @DecimalMin(value = "0.00", message = "El cupo disponible no puede ser negativo")
    private BigDecimal cupoDisponible;
    
    @NotNull(message = "El día de corte es requerido")
    @Min(value = 1, message = "El día de corte debe ser entre 1 y 31")
    @Max(value = 31, message = "El día de corte debe ser entre 1 y 31")
    private Integer diaCorte;
    
    @NotNull(message = "La fecha de emisión es requerida")
    @PastOrPresent(message = "La fecha de emisión no puede ser futura")
    private LocalDate fechaEmision;
    
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ACT|INA|BLO", message = "El estado debe ser ACT, INA O BLO")
    private String estado;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
} 
