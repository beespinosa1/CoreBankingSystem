package com.banquito.cbs.aplicacion.cliente.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PERSONA_JURIDICA")
public class PersonaJuridica implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_juridica_seq")
    @SequenceGenerator(name = "persona_juridica_seq", sequenceName = "persona_juridica_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "PERSONA_NATURAL_ID", nullable = false)
    private Integer personaNaturalId;

    @NotNull
    @Column(name = "RUC", length = 13)
    private String ruc;

    @NotNull
    @Column(name = "RAZON_SOCIAL", length = 256)
    private String razonSocial;

    @NotNull
    @Column(name = "NOMBRE_COMERCIAL", length = 128)
    private String nombreComercial;

    @NotNull
    @Column(name = "EMAIL", length = 64)
    private String email;

    @NotNull
    @Column(name = "NUMERO_TELEFONICO", length = 10)
    private String numeroTelefonico;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_CONSTITUCION")
    private LocalDate fechaConstitucion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PERSONA_NATURAL_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    private PersonaNatural personaNatural;

    public PersonaJuridica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Integer getPersonaNaturalId() {
        return personaNaturalId;
    }

    public void setPersonaNaturalId(@NotNull Integer personaNaturalId) {
        this.personaNaturalId = personaNaturalId;
    }

    public @NotNull String getRuc() {
        return ruc;
    }

    public void setRuc(@NotNull String ruc) {
        this.ruc = ruc;
    }

    public @NotNull String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(@NotNull String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public @NotNull String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(@NotNull String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public @NotNull String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(@NotNull String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public @NotNull LocalDate getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(@NotNull LocalDate fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
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

    public @NotNull PersonaNatural getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(@NotNull PersonaNatural personaNatural) {
        this.personaNatural = personaNatural;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaJuridica that = (PersonaJuridica) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonaJuridica{" +
                "id=" + id +
                ", personaNaturalId=" + personaNaturalId +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", nombreComercial='" + nombreComercial + '\'' +
                ", email='" + email + '\'' +
                ", numeroTelefonico='" + numeroTelefonico + '\'' +
                ", fechaConstitucion=" + fechaConstitucion +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", personaNatural=" + personaNatural +
                '}';
    }
}
