package com.banquito.cbs.compartido.utilidades;

import com.banquito.cbs.compartido.respuestas.RespuestaApi;

import java.util.Map;

public class UtilidadRespuesta {
    private static final String VERSION_API = "1.0";
    private static final String SERVIDOR = "API Core Banking System";
    private static final String URL_DOCUMENTACION = "https://www.espe.edu.ec/";

    public static <T> RespuestaApi<T> exito(T datos) {
        RespuestaApi.Meta meta = new RespuestaApi.Meta(VERSION_API, SERVIDOR, URL_DOCUMENTACION);
        return new RespuestaApi<>(datos, meta);
    }

    public static RespuestaApi<?> error(String codigo, String mensaje, String detalles, Map<String, String> errores) {
        RespuestaApi.DetallesError detallesError = new RespuestaApi.DetallesError(codigo, mensaje, detalles, errores);
        RespuestaApi.Meta meta = new RespuestaApi.Meta(VERSION_API, SERVIDOR, URL_DOCUMENTACION);
        return new RespuestaApi<>(detallesError, meta);
    }

    public static RespuestaApi<?> error(String codigo, String mensaje, String detalles) {
        return error(codigo, mensaje, detalles, null);
    }
}
