package com.banquito.cbs.aplicacion.cliente.controlador.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonaJuridicaDTO {
    
    private Integer id;
    private Integer personaNaturalId;
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private String email;
    private String numeroTelefonico;
    private LocalDate fechaConstitucion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private PersonaNaturalDTO personaNatural;
} 