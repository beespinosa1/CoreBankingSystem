package com.banquito.cbs.aplicacion.autenticacion.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.autenticacion.controlador.dto.UsuarioDto;
import com.banquito.cbs.aplicacion.autenticacion.modelo.Usuario;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {
    
    UsuarioDto toDto(Usuario model);
    
    Usuario toModel(UsuarioDto dto);
    
} 