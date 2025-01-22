package com.banquito.cbs.aplicacion.producto.modelo;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CUENTA")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_seq")
    @SequenceGenerator(name = "cuenta_seq", sequenceName = "cuenta_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(name = "CLIENTE_ID")
    private Integer clienteId;

    @NotNull
    @Column(name = "TIPO", length = 3)
    private String tipo;

    @NotNull
    @Column(name = "NUMERO", length = 16)
    private String numero;

    @NotNull
    @Column(name = "SALDO_TOTAL", precision = 18, scale = 2)
    private BigDecimal saldoTotal;

    @NotNull
    @Column(name = "SALDO_DISPONIBLE", precision = 18, scale = 2)
    private BigDecimal saldoDisponible;

    @NotNull
    @Column(name = "SALDO_ACREDITAR", precision = 18, scale = 2)
    private BigDecimal saldoAcreditar;

    @NotNull
    @Column(name = "ESTADO", length = 3)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Cliente cliente;

    public Cuenta() {}

    public Cuenta(Integer id) {
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

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public @NotNull String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public @NotNull BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(@NotNull BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public @NotNull BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(@NotNull BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public @NotNull BigDecimal getSaldoAcreditar() {
        return saldoAcreditar;
    }

    public void setSaldoAcreditar(@NotNull BigDecimal saldoAcreditar) {
        this.saldoAcreditar = saldoAcreditar;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", tipo='" + tipo + '\'' +
                ", numero='" + numero + '\'' +
                ", saldoTotal=" + saldoTotal +
                ", saldoDisponible=" + saldoDisponible +
                ", saldoAcreditar=" + saldoAcreditar +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
