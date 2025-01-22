package com.banquito.cbs.aplicacion.cliente.controlador.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {
    
    private Integer id;
    private Integer personaNaturalId;
    private Integer personaJuridicaId;
    private String tipo;
    private BigDecimal ingresoPromedioMensual;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private PersonaNaturalDTO personaNatural;
    private PersonaJuridicaDTO personaJuridica;
} 