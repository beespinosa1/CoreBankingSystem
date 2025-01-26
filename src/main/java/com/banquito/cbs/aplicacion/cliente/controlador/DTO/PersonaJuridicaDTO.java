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
public class PersonaJuridicaDto {
    private Integer id;
    private Integer personaNaturalId;
    
    @NotBlank(message = "El RUC es requerido")
    @Size(min = 13, max = 13, message = "El RUC debe tener 13 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El RUC debe contener solo números")
    private String ruc;
    
    @NotBlank(message = "La razón social es requerida")
    @Size(min = 3, max = 100, message = "La razón social debe tener entre 3 y 100 caracteres")
    private String razonSocial;
    
    @Size(max = 100, message = "El nombre comercial no puede exceder los 100 caracteres")
    private String nombreComercial;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    @Size(max = 128, message = "El email no puede exceder los 128 caracteres")
    private String email;
    
    @NotBlank(message = "El número telefónico es requerido")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El número telefónico debe tener entre 10 y 15 dígitos")
    private String numeroTelefonico;
    
    @NotNull(message = "La fecha de constitución es requerida")
    @Past(message = "La fecha de constitución debe ser una fecha pasada")
    private LocalDate fechaConstitucion;
    
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    private LocalDateTime fechaCreacion;
    
    @PastOrPresent(message = "La fecha de actualización no puede ser futura")
    private LocalDateTime fechaActualizacion;
    
    private PersonaNaturalDto personaNatural;
} 