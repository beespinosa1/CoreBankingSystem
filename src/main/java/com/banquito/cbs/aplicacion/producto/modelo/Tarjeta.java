package com.banquito.cbs.aplicacion.producto.modelo;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
@Table(name = "TARJETA")
public class Tarjeta implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarjeta_seq")
    @SequenceGenerator(name = "tarjeta_seq", sequenceName = "tarjeta_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "CLIENTE_ID")
    private Integer clienteId;

    @NotNull
    @Column(name = "NUMERO", length = 256)
    private String numero;

    @Column(name = "CLAVE", length = 256)
    private String clave;

    @NotNull
    @Column(name = "FECHA_EXPIRACION")
    private LocalDate fechaExpiracion;

    @NotNull
    @Column(name = "CVV", length = 256)
    private String cvv;

    @NotNull
    @Column(name = "CUPO_APROBADO", precision = 18, scale = 2)
    private BigDecimal cupoAprobado;

    @NotNull
    @Column(name = "CUPO_DISPONIBLE", precision = 18, scale = 2)
    private BigDecimal cupoDisponible;

    @NotNull
    @Column(name = "DIA_CORTE")
    private Integer diaCorte;

    @NotNull
    @Column(name = "FECHA_EMISION")
    private LocalDate fechaEmision;

    @NotNull
    @Column(name = "ESTADO", length = 3)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Cliente cliente;

    public Tarjeta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull Integer clienteId) {
        this.clienteId = clienteId;
    }

    public @NotNull String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public @NotNull String getClave() {
        return clave;
    }

    public void setClave(@NotNull String clave) {
        this.clave = clave;
    }

    public @NotNull LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(@NotNull LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public @NotNull String getCvv() {
        return cvv;
    }

    public void setCvv(@NotNull String cvv) {
        this.cvv = cvv;
    }

    public @NotNull BigDecimal getCupoAprobado() {
        return cupoAprobado;
    }

    public void setCupoAprobado(@NotNull BigDecimal cupoAprobado) {
        this.cupoAprobado = cupoAprobado;
    }

    public @NotNull BigDecimal getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(@NotNull BigDecimal cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public @NotNull Integer getDiaCorte() {
        return diaCorte;
    }

    public void setDiaCorte(@NotNull Integer diaCorte) {
        this.diaCorte = diaCorte;
    }

    public @NotNull LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(@NotNull LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public @NotNull Cliente getCliente() {
        return cliente;
    }

    public void setCliente(@NotNull Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return Objects.equals(id, tarjeta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", numero='" + numero + '\'' +
                ", clave='" + clave + '\'' +
                ", fechaExpiracion=" + fechaExpiracion +
                ", cvv='" + cvv + '\'' +
                ", cupoAprobado=" + cupoAprobado +
                ", cupoDisponible=" + cupoDisponible +
                ", diaCorte=" + diaCorte +
                ", fechaEmision=" + fechaEmision +
                ", estado='" + estado + '\'' +
                '}';
    }
}
