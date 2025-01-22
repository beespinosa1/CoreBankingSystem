package com.banquito.cbs.aplicacion.cliente.excepcion;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String data;
    private final String entity;

    public NotFoundException(String data, String entity) {
        super();
        this.data = data;
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "No se ha encontrado ninguna coincidencia para: " + entity + 
        ", con el dato: " + data;
    }

}