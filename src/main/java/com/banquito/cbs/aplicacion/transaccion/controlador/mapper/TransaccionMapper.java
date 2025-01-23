package com.banquito.cbs.aplicacion.transaccion.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.transaccion.controlador.DTO.TransaccionDTO;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {DetalleTransaccionMapper.class}
)
public interface TransaccionMapper {
    TransaccionDTO toDTO(Transaccion model);
    Transaccion toModel(TransaccionDTO transaccionDTO);
} 