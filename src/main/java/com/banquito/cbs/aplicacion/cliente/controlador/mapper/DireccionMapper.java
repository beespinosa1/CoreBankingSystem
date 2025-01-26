package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.dto.DireccionDto;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DireccionMapper {

    DireccionDto toDTO(Direccion model);
    Direccion toModel(DireccionDto direccionDTO);

} 