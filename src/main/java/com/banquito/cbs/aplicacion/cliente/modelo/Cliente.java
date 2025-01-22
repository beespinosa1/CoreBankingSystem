package com.banquito.cbs.aplicacion.cliente.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PERSONA_NATURAL_ID")
    private Integer personaNaturalId;

    @Column(name = "PERSONA_JURIDICA_ID")
    private Integer personaJuridicaId;

    @NotNull
    @Column(name = "TIPO", length = 3, nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "INGRESO_PROMEDIO_MENSUAL", precision = 18, scale = 2, nullable = false)
    private BigDecimal ingresoPromedioMensual;

    @NotNull
    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", length = 50)
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION", length = 50)
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "PERSONA_NATURAL_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    private PersonaNatural personaNatural;

    @ManyToOne
    @JoinColumn(name = "PERSONA_JURIDICA_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    private PersonaJuridica personaJuridica;

    public Cliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonaNaturalId() {
        return personaNaturalId;
    }

    public void setPersonaNaturalId(Integer personaNaturalId) {
        this.personaNaturalId = personaNaturalId;
    }

    public Integer getPersonaJuridicaId() {
        return personaJuridicaId;
    }

    public void setPersonaJuridicaId(Integer personaJuridicaId) {
        this.personaJuridicaId = personaJuridicaId;
    }

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public @NotNull BigDecimal getIngresoPromedioMensual() {
        return ingresoPromedioMensual;
    }

    public void setIngresoPromedioMensual(@NotNull BigDecimal ingresoPromedioMensual) {
        this.ingresoPromedioMensual = ingresoPromedioMensual;
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

    public PersonaNatural getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(PersonaNatural personaNatural) {
        this.personaNatural = personaNatural;
    }

    public PersonaJuridica getPersonaJuridica() {
        return personaJuridica;
    }

    public void setPersonaJuridica(PersonaJuridica personaJuridica) {
        this.personaJuridica = personaJuridica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", personaNaturalId=" + personaNaturalId +
                ", personaJuridicaId=" + personaJuridicaId +
                ", tipo='" + tipo + '\'' +
                ", ingresoPromedioMensual=" + ingresoPromedioMensual +
                ", estado='" + estado + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                '}';
    }
}
