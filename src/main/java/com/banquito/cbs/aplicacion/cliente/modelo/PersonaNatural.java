package com.banquito.cbs.aplicacion.cliente.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PERSONA_NATURAL")
public class PersonaNatural implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_natural_seq")
    @SequenceGenerator(name = "persona_natural_seq", sequenceName = "persona_natural_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "TIPO_IDENTIFICACION", length = 3, nullable = false)
    private String tipoIdentificacion;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "IDENTIFICACION", length = 13, nullable = false)
    private String identificacion;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "PRIMER_NOMBRE", length = 32, nullable = false)
    private String primerNombre;

    @Column(name = "SEGUNDO_NOMBRE", length = 32)
    private String segundoNombre;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "PRIMER_APELLIDO", length = 32, nullable = false)
    private String primerApellido;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "SEGUNDO_APELLIDO", length = 32, nullable = false)
    private String segundoApellido;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Column(name = "NUMERO_TELEFONICO", length = 10, nullable = false)
    private String numeroTelefonico;

    @NotNull(message = "El campo es obligatorio")
    @NotBlank(message = "El campo no puede estar vacío")
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    public PersonaNatural() {}

    public PersonaNatural(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(@NotNull String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public @NotNull String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(@NotNull String identificacion) {
        this.identificacion = identificacion;
    }

    public @NotNull String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(@NotNull String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public @NotNull String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(@NotNull String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public @NotNull String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(@NotNull String segundoApellido) {
        this.segundoApellido = segundoApellido;
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

    public @NotNull LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaNatural that = (PersonaNatural) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonaNatural{" +
                "id=" + id +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", email='" + email + '\'' +
                ", numeroTelefonico='" + numeroTelefonico + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
