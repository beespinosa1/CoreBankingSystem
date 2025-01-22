package com.banquito.cbs.compartido.excepciones;

public class OperacionInvalidaExcepcion extends RuntimeException {
    public OperacionInvalidaExcepcion(String message) {
        super(message);
    }
}