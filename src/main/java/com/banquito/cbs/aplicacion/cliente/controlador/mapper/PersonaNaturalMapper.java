package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaNaturalDto;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonaNaturalMapper {

    PersonaNaturalDto toDto(PersonaNatural model);

    PersonaNatural toModel(PersonaNaturalDto personaNaturalDto);

}
