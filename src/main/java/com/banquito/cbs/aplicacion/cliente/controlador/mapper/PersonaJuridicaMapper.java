package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaJuridicaDto;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaJuridica;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonaJuridicaMapper {

    PersonaJuridicaDto toDto(PersonaJuridica model);

    PersonaJuridica toModel(PersonaJuridicaDto personaJuridicaDto);

}