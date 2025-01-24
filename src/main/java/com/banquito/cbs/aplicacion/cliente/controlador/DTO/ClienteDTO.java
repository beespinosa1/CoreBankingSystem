package com.banquito.cbs.aplicacion.cliente.controlador.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    
    private Integer id;
    
    private Integer personaNaturalId;
    
    private Integer personaJuridicaId;
    
    @NotBlank(message = "El tipo de cliente es requerido")
    @Pattern(regexp = "NAT|JUR", message = "El tipo de cliente debe ser NAT o JUR")
    private String tipo;
    
    @NotNull(message = "El ingreso promedio mensual es requerido")
    @DecimalMin(value = "0.01", message = "El ingreso promedio mensual debe ser mayor a 0")
    @DecimalMax(value = "999999999.99", message = "El ingreso promedio mensual excede el límite permitido")
    private BigDecimal ingresoPromedioMensual;
    
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ACT|INA|BLO", message = "El estado debe ser ACT, INA o BLO")
    private String estado;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
    
    private PersonaNaturalDTO personaNatural;
    
    private PersonaJuridicaDTO personaJuridica;
} 