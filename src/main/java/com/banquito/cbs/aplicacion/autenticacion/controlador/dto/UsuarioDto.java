package com.banquito.cbs.aplicacion.autenticacion.controlador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Integer id;
    private Integer clienteId;
    
    @NotBlank(message = "El rol es requerido")
    @Pattern(regexp = "ADM|USR", message = "El rol debe ser ADM o USR")
    private String rol;
    
    @NotBlank(message = "El usuario es requerido")
    @Size(min = 3, max = 32, message = "El usuario debe tener entre 3 y 32 caracteres")
    private String usuario;
    
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, max = 256, message = "La contraseña debe tener entre 8 y 256 caracteres")
    private String contrasenia;
    
    @Size(max = 256, message = "La URL de la imagen no puede exceder los 256 caracteres")
    private String img;
    
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "ACT|INA|BLO", message = "El estado debe ser ACT, INA o BLO")
    private String estado;
    
    private LocalDateTime fechaUltimoIngreso;
    
    @Size(max = 15, message = "La IP no puede exceder los 15 caracteres")
    private String ipUltimoIngreso;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDate fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDate fechaActualizacion;
} 