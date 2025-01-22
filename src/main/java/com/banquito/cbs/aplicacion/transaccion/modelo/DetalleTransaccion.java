package com.banquito.cbs.aplicacion.transaccion.modelo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "DETALLE_TRANSACCION")
public class    DetalleTransaccion implements Serializable
{
    @Id
    @Column(name = "TRANSACCION_ID")
    private Integer transaccionId;

    @Column(name = "TIPO_CUENTA", length = 3)
    private String tipoCuenta;

    @Column(name = "BENEFICIARIO", length = 128)
    private String beneficiario;

    @Column(name = "CUENTA_ORIGEN", length = 16)
    private String cuentaOrigen;

    @Column(name = "CUENTA_DESTINO", length = 16)
    private String cuentaDestino;

    @Column(name = "BIN_BANCO_ORIGEN", length = 8)
    private String binBancoOrigen;

    @Column(name = "BIN_BANCO_DESTINO", length = 8)
    private String binBancoDestino;

    @Column(name = "DESCRIPCION", length = 128)
    private String descripcion;

    @Lob
    @Column(name = "DETALLE_JSON")
    private String detalleJson;

    @Column(name = "FECHA_AUTORIZACION")
    private LocalDate fechaAutorizacion;

    @Transient
    private Map<String, Object> detalleMap;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @PostLoad
    private void postLoad() {
        if (this.detalleJson != null) {
            try {
                this.detalleMap = OBJECT_MAPPER.readValue(this.detalleJson, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (this.detalleMap != null) {
            try {
                this.detalleJson = OBJECT_MAPPER.writeValueAsString(this.detalleMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DetalleTransaccion() {}

    public DetalleTransaccion(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getBinBancoOrigen() {
        return binBancoOrigen;
    }

    public void setBinBancoOrigen(String binBancoOrigen) {
        this.binBancoOrigen = binBancoOrigen;
    }

    public String getBinBancoDestino() {
        return binBancoDestino;
    }

    public void setBinBancoDestino(String binBancoDestino) {
        this.binBancoDestino = binBancoDestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalleJson() {
        return detalleJson;
    }

    public void setDetalleJson(String detalleJson) {
        this.detalleJson = detalleJson;
    }

    public LocalDate getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(LocalDate fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public Map<String, Object> getDetalleMap() {
        return detalleMap;
    }

    public void setDetalleMap(Map<String, Object> detalleMap) {
        this.detalleMap = detalleMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleTransaccion that = (DetalleTransaccion) o;
        return Objects.equals(transaccionId, that.transaccionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transaccionId);
    }

    @Override
    public String toString() {
        return "DetalleTransaccion{" +
                "transaccionId=" + transaccionId +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", beneficiario='" + beneficiario + '\'' +
                ", cuentaOrigen='" + cuentaOrigen + '\'' +
                ", cuentaDestino='" + cuentaDestino + '\'' +
                ", binBancoOrigen='" + binBancoOrigen + '\'' +
                ", binBancoDestino='" + binBancoDestino + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", detalleJson='" + detalleJson + '\'' +
                ", fechaAutorizacion=" + fechaAutorizacion +
                '}';
    }
}
