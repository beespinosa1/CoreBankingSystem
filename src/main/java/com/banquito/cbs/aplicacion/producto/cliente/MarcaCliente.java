package com.banquito.cbs.aplicacion.producto.cliente;

import com.banquito.cbs.aplicacion.producto.dto.CrearTarjetaDto;
import com.banquito.cbs.aplicacion.producto.dto.TarjetaMarcaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "MARCA-API", url = "${external.marca.api.base-url}")
public interface MarcaCliente {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/tarjetas")
    TarjetaMarcaDto crearTarjeta(CrearTarjetaDto datosTarjeta);
}
