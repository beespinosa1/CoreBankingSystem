package com.banquito.cbs.aplicacion.transaccion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "DIFERIDO")
public class Diferido implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diferido_seq")
    @SequenceGenerator(name = "diferido_seq", sequenceName = "diferido_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "TRANSACCION_ID", nullable = false)
    private Integer transaccionId;

    @NotNull
    @Column(name = "CUOTAS", nullable = false)
    private Integer cuotas;

    @NotNull
    @Column(name = "CUOTAS_CANCELADAS", nullable = false)
    private Integer cuotasCanceladas;

    @NotNull
    @Column(name = "TAZA_INTERES", precision = 5, scale = 2, nullable = false)
    private BigDecimal tazaInteres;

    @NotNull
    @Column(name = "VALOR_CUOTA", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorCuota;

    @NotNull
    @Column(name = "VALOR_DEUDA", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorDeuda;

    @NotNull
    @Column(name = "VALOR_RESTANTE", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorRestante;

    @NotNull
    @Column(name = "VALOR_INTERES", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorInteres;

    @NotNull
    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRANSACCION_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Transaccion transaccion;

    public Diferido() {}

    public Diferido(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(@NotNull Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public @NotNull Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(@NotNull Integer cuotas) {
        this.cuotas = cuotas;
    }

    public @NotNull Integer getCuotasCanceladas() {
        return cuotasCanceladas;
    }

    public void setCuotasCanceladas(@NotNull Integer cuotasCanceladas) {
        this.cuotasCanceladas = cuotasCanceladas;
    }

    public @NotNull BigDecimal getTazaInteres() {
        return tazaInteres;
    }

    public void setTazaInteres(@NotNull BigDecimal tazaInteres) {
        this.tazaInteres = tazaInteres;
    }

    public @NotNull BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(@NotNull BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public @NotNull BigDecimal getValorDeuda() {
        return valorDeuda;
    }

    public void setValorDeuda(@NotNull BigDecimal valorDeuda) {
        this.valorDeuda = valorDeuda;
    }

    public @NotNull BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(@NotNull BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public @NotNull BigDecimal getValorInteres() {
        return valorInteres;
    }

    public void setValorInteres(@NotNull BigDecimal valorInteres) {
        this.valorInteres = valorInteres;
    }

    public @NotNull String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull String estado) {
        this.estado = estado;
    }

    public @NotNull Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(@NotNull Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diferido diferido = (Diferido) o;
        return Objects.equals(id, diferido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Diferido{" +
                "id=" + id +
                ", transaccionId=" + transaccionId +
                ", cuotas=" + cuotas +
                ", cuotasCanceladas=" + cuotasCanceladas +
                ", tazaInteres=" + tazaInteres +
                ", valorCuota=" + valorCuota +
                ", valorDeuda=" + valorDeuda +
                ", valorRestante=" + valorRestante +
                ", valorInteres=" + valorInteres +
                ", estado='" + estado + '\'' +
                '}';
    }
}
