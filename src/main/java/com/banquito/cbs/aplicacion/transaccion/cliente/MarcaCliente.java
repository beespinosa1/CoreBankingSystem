package com.banquito.cbs.aplicacion.transaccion.cliente;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MARCA-API", url = "${external.marca.api.base-url}")
public class MarcaCliente {
}
