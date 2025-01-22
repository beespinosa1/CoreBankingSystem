package com.banquito.cbs.aplicacion.externo.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "SEGURIDAD_MARCA")
public class SeguridadMarca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seguridad_marca_seq")
    @SequenceGenerator(name = "seguridad_marca_seq", sequenceName = "seguridad_marca_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "CLAVE_AUTENTICACION", length = 256, nullable = false)
    private String claveAutenticacion;

    @NotNull
    @Column(name = "URI_BASE", length = 256, nullable = false)
    private String uriBase;

    @Column(name = "FECHA_CREACION")
    private LocalDate fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    public SeguridadMarca() {
    }

    public SeguridadMarca(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull String nombre) {
        this.nombre = nombre;
    }

    public @NotNull String getClaveAutenticacion() {
        return claveAutenticacion;
    }

    public void setClaveAutenticacion(@NotNull String claveAutenticacion) {
        this.claveAutenticacion = claveAutenticacion;
    }

    public @NotNull String getUriBase() {
        return uriBase;
    }

    public void setUriBase(@NotNull String uriBase) {
        this.uriBase = uriBase;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeguridadMarca that = (SeguridadMarca) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SeguridadMarca{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", claveAutenticacion='" + claveAutenticacion + '\'' +
                ", uriBase='" + uriBase + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}

