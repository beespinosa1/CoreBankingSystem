package com.banquito.cbs.aplicacion.transaccion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "DISPUTA")
public class Disputa implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disputa_seq")
    @SequenceGenerator(name = "disputa_seq", sequenceName = "disputa_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "TRANSACCION_ID", nullable = false)
    private Integer transaccionId;

    @NotNull
    @Column(name = "RAZON", length = 256)
    private String razon;

    @NotNull
    @Column(name = "ESTADO", length = 3)
    private String estado;

    @NotNull
    @Column(name = "FECHA_RESOLUCION")
    private LocalDate fechaResolucion;

    @Column(name = "FECHA_CREACION")
    private LocalDate fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRANSACCION_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Transaccion transaccion;

    public Disputa() {}

    public Disputa(Integer id) {
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

    public @NotNull String getRazon() {
        return razon;
    }

    public void setRazon(@NotNull String razon) {
        this.razon = razon;
    }

    public @NotNull String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull String estado) {
        this.estado = estado;
    }

    public @NotNull LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(@NotNull LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDate fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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
        Disputa disputa = (Disputa) o;
        return Objects.equals(id, disputa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Disputa{" +
                "id=" + id +
                ", transaccionId=" + transaccionId +
                ", razon='" + razon + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaResolucion=" + fechaResolucion +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
