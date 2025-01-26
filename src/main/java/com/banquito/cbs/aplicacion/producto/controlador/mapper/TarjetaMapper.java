package com.banquito.cbs.aplicacion.producto.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.producto.dto.TarjetaDto;
import com.banquito.cbs.aplicacion.producto.modelo.Tarjeta;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TarjetaMapper {
    TarjetaDto toDto(Tarjeta model);

    Tarjeta toModel(TarjetaDto tarjetaDto);
}