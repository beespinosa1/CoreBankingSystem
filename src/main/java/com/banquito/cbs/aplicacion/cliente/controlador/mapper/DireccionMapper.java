package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.DireccionDTO;
import com.banquito.cbs.aplicacion.cliente.modelo.Direccion;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DireccionMapper {

    DireccionDTO toDTO(Direccion model);
    Direccion toModel(DireccionDTO direccionDTO);

} 