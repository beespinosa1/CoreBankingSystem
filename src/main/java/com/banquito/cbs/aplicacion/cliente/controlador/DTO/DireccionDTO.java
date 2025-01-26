package com.banquito.cbs.aplicacion.cliente.controlador.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDto {
    
    private Integer id;
    
    private Integer personaNaturalId;
    
    private Integer personaJuridicaId;
    
    @NotBlank(message = "La provincia es requerida")
    @Size(min = 3, max = 64, message = "La provincia debe tener entre 3 y 64 caracteres")
    private String provincia;
    
    @NotBlank(message = "La ciudad es requerida")
    @Size(min = 3, max = 64, message = "La ciudad debe tener entre 3 y 64 caracteres")
    private String ciudad;
    
    @NotBlank(message = "El cantón es requerido")
    @Size(min = 3, max = 64, message = "El cantón debe tener entre 3 y 64 caracteres")
    private String canton;
    
    @NotBlank(message = "El sector es requerido")
    @Size(min = 3, max = 64, message = "El sector debe tener entre 3 y 64 caracteres")
    private String sector;
    
    @NotBlank(message = "La calle principal es requerida")
    @Size(min = 3, max = 128, message = "La calle principal debe tener entre 3 y 128 caracteres")
    private String callePrincipal;
    
    @Size(max = 128, message = "La calle secundaria no puede exceder los 128 caracteres")
    private String calleSecundaria;
    
    @NotBlank(message = "El número es requerido")
    @Size(max = 20, message = "El número no puede exceder los 20 caracteres")
    private String numero;
    
    @Size(max = 256, message = "La referencia no puede exceder los 256 caracteres")
    private String referencia;
    
    @Size(max = 256, message = "El detalle no puede exceder los 256 caracteres")
    private String detalle;
    
    private PersonaNaturalDTO personaNatural;
    
    private PersonaJuridicaDTO personaJuridica;
} 