package com.banquito.cbs.aplicacion.transaccion.controlador.dto;

import java.time.LocalDate;
import java.util.Map;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleTransaccionDto {

    private Integer transaccionId;

    @NotBlank(message = "El tipo de cuenta es requerido")
    @Pattern(regexp = "AHO|COR|PLA", message = "El tipo de cuenta debe ser AHO, COR o PLA")
    private String tipoCuenta;

    @NotBlank(message = "El beneficiario es requerido")
    @Size(min = 3, max = 128, message = "El beneficiario debe tener entre 3 y 128 caracteres")
    private String beneficiario;

    @NotBlank(message = "La cuenta de origen es requerida")
    @Size(min = 10, max = 20, message = "La cuenta de origen debe tener entre 10 y 20 caracteres")
    private String cuentaOrigen;

    @NotBlank(message = "La cuenta de destino es requerida")
    @Size(min = 10, max = 20, message = "La cuenta de destino debe tener entre 10 y 20 caracteres")
    private String cuentaDestino;

    @Size(min = 6, max = 6, message = "El BIN del banco de origen debe tener 6 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El BIN del banco de origen debe contener solo números")
    private String binBancoOrigen;

    @Size(min = 6, max = 6, message = "El BIN del banco de destino debe tener 6 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El BIN del banco de destino debe contener solo números")
    private String binBancoDestino;

    @Size(max = 256, message = "La descripción no puede exceder los 256 caracteres")
    private String descripcion;

    @Size(max = 4000, message = "El detalle JSON no puede exceder los 4000 caracteres")
    private String detalleJson;

    @PastOrPresent(message = "La fecha de autorización no puede ser futura")
    private LocalDate fechaAutorizacion;

    private Map<String, Object> detalleMap;
}