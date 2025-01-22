package com.banquito.cbs.aplicacion.cliente.controlador.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DireccionDTO {
    
    private Integer id;
    private Integer personaNaturalId;
    private Integer personaJuridicaId;
    private String provincia;
    private String ciudad;
    private String canton;
    private String sector;
    private String callePrincipal;
    private String calleSecundaria;
    private String numero;
    private String referencia;
    private String detalle;
    private PersonaNaturalDTO personaNatural;
    private PersonaJuridicaDTO personaJuridica;
} 