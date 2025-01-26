package com.banquito.cbs.aplicacion.cliente.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    @OneToMany
    @JoinColumn(
            name = "PERSONA_NATURAL_ID",
            referencedColumnName = "ID",
            insertable = false,
            updatable = false
    )
    private List<Direccion> direcciones;

    public PersonaNatural(Integer id) {
        this.id = id;
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
