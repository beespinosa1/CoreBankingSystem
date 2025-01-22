package com.banquito.cbs.aplicacion.cliente.excepcion;

public class DuplicateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String data;
    private final String entity;

    public DuplicateException(String data, String entity) {
        super();
        this.data = data;
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "Ya existe un registro para: " + entity + 
        ", con el dato: " + data;
    }

}