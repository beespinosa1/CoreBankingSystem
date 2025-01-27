package com.banquito.cbs.aplicacion.transaccion.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.transaccion.dto.TransaccionDto;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransaccionMapper {
    TransaccionDto toDto(Transaccion transaccion);

    Transaccion toModel(TransaccionDto dto);
}