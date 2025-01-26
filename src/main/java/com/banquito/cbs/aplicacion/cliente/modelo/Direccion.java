package com.banquito.cbs.aplicacion.cliente.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DIRECCION")
public class Direccion implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direccion_seq")
    @SequenceGenerator(name = "direccion_seq", sequenceName = "direccion_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PERSONA_NATURAL_ID")
    private Integer personaNaturalId;

    @Column(name = "PERSONA_JURIDICA_ID")
    private Integer personaJuridicaId;

    @NotNull
    @Column(name = "PROVINCIA", length = 32, nullable = false)
    private String provincia;

    @NotNull
    @Column(name = "CIUDAD", length = 32, nullable = false)
    private String ciudad;

    @NotNull
    @Column(name = "CANTON", length = 32, nullable = false)
    private String canton;

    @NotNull
    @Column(name = "SECTOR", length = 32, nullable = false)
    private String sector;

    @NotNull
    @Column(name = "CALLE_PRINCIPAL", length = 64, nullable = false)
    private String callePrincipal;

    @NotNull
    @Column(name = "CALLE_SECUNDARIA", length = 64, nullable = false)
    private String calleSecundaria;

    @NotNull
    @Column(name = "NUMERO", length = 16, nullable = false)
    private String numero;

    @NotNull
    @Column(name = "REFERENCIA", length = 64, nullable = false)
    private String referencia;

    @Column(name = "DETALLE", length = 256)
    private String detalle;

    @ManyToOne
    @JoinColumn(name = "PERSONA_NATURAL_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private PersonaNatural personaNatural;

    @ManyToOne
    @JoinColumn(name = "PERSONA_JURIDICA_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private PersonaJuridica personaJuridica;

    public Direccion(Integer id) {
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

    public @NotNull String getProvincia() {
        return provincia;
    }

    public void setProvincia(@NotNull String provincia) {
        this.provincia = provincia;
    }

    public @NotNull String getCiudad() {
        return ciudad;
    }

    public void setCiudad(@NotNull String ciudad) {
        this.ciudad = ciudad;
    }

    public @NotNull String getCanton() {
        return canton;
    }

    public void setCanton(@NotNull String canton) {
        this.canton = canton;
    }

    public @NotNull String getSector() {
        return sector;
    }

    public void setSector(@NotNull String sector) {
        this.sector = sector;
    }

    public @NotNull String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(@NotNull String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public @NotNull String getCalleSecundaria() {
        return calleSecundaria;
    }

    public void setCalleSecundaria(@NotNull String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    public @NotNull String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public @NotNull String getReferencia() {
        return referencia;
    }

    public void setReferencia(@NotNull String referencia) {
        this.referencia = referencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
        Direccion direccion = (Direccion) o;
        return Objects.equals(id, direccion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", personaNaturalId=" + personaNaturalId +
                ", personaJuridicaId=" + personaJuridicaId +
                ", provincia='" + provincia + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", canton='" + canton + '\'' +
                ", sector='" + sector + '\'' +
                ", callePrincipal='" + callePrincipal + '\'' +
                ", calleSecundaria='" + calleSecundaria + '\'' +
                ", numero='" + numero + '\'' +
                ", referencia='" + referencia + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
