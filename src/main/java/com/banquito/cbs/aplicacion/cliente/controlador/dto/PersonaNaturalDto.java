package com.banquito.cbs.aplicacion.cliente.controlador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaNaturalDto {
    
    private Integer id;
    
    @NotBlank(message = "El tipo de identificación es requerido")
    @Pattern(regexp = "CED|RUC|PAS", message = "El tipo de identificación debe ser CED, RUC o PAS")
    private String tipoIdentificacion;
    
    @NotBlank(message = "La identificación es requerida")
    @Size(min = 10, max = 13, message = "La identificación debe tener entre 10 y 13 caracteres")
    private String identificacion;
    
    @NotBlank(message = "El primer nombre es requerido")
    @Size(min = 2, max = 50, message = "El primer nombre debe tener entre 2 y 50 caracteres")
    private String primerNombre;
    
    @Size(max = 50, message = "El segundo nombre no puede exceder los 50 caracteres")
    private String segundoNombre;
    
    @NotBlank(message = "El primer apellido es requerido")
    @Size(min = 2, max = 50, message = "El primer apellido debe tener entre 2 y 50 caracteres")
    private String primerApellido;
    
    @Size(max = 50, message = "El segundo apellido no puede exceder los 50 caracteres")
    private String segundoApellido;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    @Size(max = 128, message = "El email no puede exceder los 128 caracteres")
    private String email;
    
    @NotBlank(message = "El número telefónico es requerido")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El número telefónico debe tener entre 10 y 15 dígitos")
    private String numeroTelefonico;
    
    @NotNull(message = "La fecha de nacimiento es requerida")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
}
