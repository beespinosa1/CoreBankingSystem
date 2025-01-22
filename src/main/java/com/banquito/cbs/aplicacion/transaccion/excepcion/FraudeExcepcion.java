package com.banquito.cbs.aplicacion.transaccion.excepcion;

public class FraudeExcepcion extends RuntimeException {
    public FraudeExcepcion(String message) {
        super(message);
    }
}