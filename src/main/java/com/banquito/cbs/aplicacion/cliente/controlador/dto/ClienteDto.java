package com.banquito.cbs.aplicacion.cliente.controlador.dto;

import java.math.BigDecimal;
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
public class ClienteDto {
    
    private Integer id;
    
    private Integer personaNaturalId;
    
    private Integer personaJuridicaId;
    
    @NotBlank(message = "El tipo de cliente es requerido")
    @Pattern(regexp = "COR|PER", 
            message = "El tipo de cliente debe ser Corporativo (COR) o Personal (PER)")
    private String tipo;
    
    @NotNull(message = "El ingreso promedio mensual es requerido")
    @DecimalMin(value = "0.01", message = "El ingreso promedio mensual debe ser mayor a 0")
    @DecimalMax(value = "999999999.99", message = "El ingreso promedio mensual excede el límite permitido")
    private BigDecimal ingresoPromedioMensual;
    
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ACT|INA", 
            message = "El estado debe ser Activo (ACT) o Inactivo (INA)")
    private String estado;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
    
    private PersonaNaturalDto personaNatural;
    
    private PersonaJuridicaDto personaJuridica;
} 
