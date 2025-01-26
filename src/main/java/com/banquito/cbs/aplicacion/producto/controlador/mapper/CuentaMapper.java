package com.banquito.cbs.aplicacion.producto.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.producto.dto.CuentaDto;
import com.banquito.cbs.aplicacion.producto.modelo.Cuenta;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CuentaMapper {
    CuentaDto toDto(Cuenta model);

    Cuenta toModel(CuentaDto cuentaDto);
}