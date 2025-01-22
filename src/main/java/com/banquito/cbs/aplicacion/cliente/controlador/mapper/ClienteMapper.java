package com.banquito.cbs.aplicacion.cliente.controlador.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.cbs.aplicacion.cliente.controlador.DTO.ClienteDTO;
import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClienteMapper {

    ClienteDTO toDTO(Cliente model);
    Cliente toModel(ClienteDTO clienteDTO);

} 