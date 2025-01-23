package com.banquito.cbs.aplicacion.transaccion.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.transaccion.controlador.DTO.DetalleTransaccionDTO;
import com.banquito.cbs.aplicacion.transaccion.modelo.DetalleTransaccion;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DetalleTransaccionMapper {
    DetalleTransaccionDTO toDTO(DetalleTransaccion model);
    DetalleTransaccion toModel(DetalleTransaccionDTO detalleTransaccionDTO);
} 