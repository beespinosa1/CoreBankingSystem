package com.banquito.cbs.aplicacion.notificacion.modelo;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "NOTIFICACION")
public class Notificacion implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacion_seq")
    @SequenceGenerator(name = "notificacion_seq", sequenceName = "notificacion_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CLIENTE_ID", nullable = false)
    private Integer clienteId;

    @Column(name = "TRANSACCION_ID")
    private Integer transaccionId;

    @NotNull
    @Column(name = "TIPO", length = 3, nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @NotNull
    @Column(name = "CONTENIDO", nullable = false)
    private String contenido;

    @NotNull
    @Column(name = "FECHA_ENVIO", nullable = false)
    private LocalDate fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "TRANSACCION_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Transaccion transaccion;

    public Notificacion() {}

    public Notificacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public @NotNull String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull String estado) {
        this.estado = estado;
    }

    public @NotNull String getContenido() {
        return contenido;
    }

    public void setContenido(@NotNull String contenido) {
        this.contenido = contenido;
    }

    public @NotNull LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(@NotNull LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacion that = (Notificacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", transaccionId=" + transaccionId +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", cliente=" + cliente +
                ", transaccion=" + transaccion +
                '}';
    }
}