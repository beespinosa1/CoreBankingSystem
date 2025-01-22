package com.banquito.cbs.compartido.excepciones;

public class EntidadDuplicadaExcepcion extends RuntimeException {
    public EntidadDuplicadaExcepcion(String message) {
        super(message);
    }
}