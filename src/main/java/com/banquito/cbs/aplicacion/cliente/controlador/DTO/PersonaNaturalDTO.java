package com.banquito.cbs.aplicacion.cliente.controlador.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaNaturalDTO {
    
    private Integer id;
    private String tipoIdentificacion;
    private String identificacion;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String numeroTelefonico;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
