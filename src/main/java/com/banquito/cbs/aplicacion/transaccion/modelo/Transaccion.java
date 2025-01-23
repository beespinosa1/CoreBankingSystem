package com.banquito.cbs.aplicacion.transaccion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TRANSACCION")
public class Transaccion implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaccion_seq")
    @SequenceGenerator(name = "transaccion_seq", sequenceName = "transaccion_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CUENTA_ID")
    private Integer cuentaId;

    @Column(name = "TARJETA_ID")
    private Integer tarjetaId;

    @NotNull
    @Column(name = "TIPO", length = 3, nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "CANAL", length = 3, nullable = false)
    private String canal;

    @NotNull
    @Column(name = "FECHA_HORA", nullable = false)
    private LocalDate fechaHora;

    @NotNull
    @Column(name = "VALOR", precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "COMISION", precision = 18, scale = 2, nullable = false)
    private BigDecimal comision;

    @NotNull
    @Column(name = "TAZA_INTERES", precision = 18, scale = 2, nullable = false)
    private BigDecimal tazaInteres;

    @NotNull
    @Column(name = "ES_DIFERIDO", nullable = false)
    private Boolean esDiferido;

    @NotNull
    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "TRANSACCION_ID", insertable = false, updatable = false)
    private DetalleTransaccion detalleTransaccion;

    public Transaccion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Integer getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Integer tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public @NotNull String getCanal() {
        return canal;
    }

    public void setCanal(@NotNull String canal) {
        this.canal = canal;
    }

    public @NotNull LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(@NotNull LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public @NotNull BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = valor;
    }

    public @NotNull BigDecimal getComision() {
        return comision;
    }

    public void setComision(@NotNull BigDecimal comision) {
        this.comision = comision;
    }

    public @NotNull BigDecimal getTazaInteres() {
        return tazaInteres;
    }

    public void setTazaInteres(@NotNull BigDecimal tazaInteres) {
        this.tazaInteres = tazaInteres;
    }

    public @NotNull Boolean getEsDiferido() {
        return esDiferido;
    }

    public void setEsDiferido(@NotNull Boolean esDiferido) {
        this.esDiferido = esDiferido;
    }

    public @NotNull String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public DetalleTransaccion getDetalleTransaccion() {
        return detalleTransaccion;
    }

    public void setDetalleTransaccion(DetalleTransaccion detalleTransaccion) {
        this.detalleTransaccion = detalleTransaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", cuentaId=" + cuentaId +
                ", tarjetaId=" + tarjetaId +
                ", tipo='" + tipo + '\'' +
                ", canal='" + canal + '\'' +
                ", fechaHora=" + fechaHora +
                ", valor=" + valor +
                ", comision=" + comision +
                ", tazaInteres=" + tazaInteres +
                ", esDiferido=" + esDiferido +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
