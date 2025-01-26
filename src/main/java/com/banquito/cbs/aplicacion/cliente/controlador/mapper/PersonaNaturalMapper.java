package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.PersonaNaturalDTO;
import com.banquito.cbs.aplicacion.cliente.modelo.PersonaNatural;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PersonaNaturalMapper {

    PersonaNaturalDTO toDTO(PersonaNatural model);
    PersonaNatural toModel(PersonaNaturalDTO personaNaturalDTO);

}
