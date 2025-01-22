package com.banquito.cbs.aplicacion.autenticacion.modelo;

import com.banquito.cbs.aplicacion.cliente.modelo.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CLIENTE_ID", nullable = false)
    private Integer clienteId;

    @NotNull
    @Column(name = "ROL", length = 3)
    private String rol;

    @NotNull
    @Column(name = "USUARIO", length = 32)
    private String usuario;

    @NotNull
    @Column(name = "CONTRASENIA", length = 256)
    private String contrasenia;

    @NotNull
    @Column(name = "IMG", length = 256)
    private String img;

    @NotNull
    @Column(name = "ESTADO", length = 3)
    private String estado;

    @Column(name = "FECHA_ULTIMO_INGRESO")
    private LocalDateTime fechaUltimoIngreso;

    @Column(name = "IP_ULTIMO_INGRESO", length = 15)
    private String ipUltimoIngreso;

    @Column(name = "FECHA_CREACION")
    private LocalDate fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDate fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Cliente cliente;

    public Usuario() {}

    public Usuario(Integer id) {
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

    public @NotNull String getRol() {
        return rol;
    }

    public void setRol(@NotNull String rol) {
        this.rol = rol;
    }

    public @NotNull String getUsuario() {
        return usuario;
    }

    public void setUsuario(@NotNull String usuario) {
        this.usuario = usuario;
    }

    public @NotNull String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(@NotNull String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public @NotNull String getEstado() {
        return estado;
    }

    public void setEstado(@NotNull String estado) {
        this.estado = estado;
    }

    public @NotNull LocalDateTime getFechaUltimoIngreso() {
        return fechaUltimoIngreso;
    }

    public void setFechaUltimoIngreso(@NotNull LocalDateTime fechaUltimoIngreso) {
        this.fechaUltimoIngreso = fechaUltimoIngreso;
    }

    public @NotNull String getIpUltimoIngreso() {
        return ipUltimoIngreso;
    }

    public void setIpUltimoIngreso(@NotNull String ipUltimoIngreso) {
        this.ipUltimoIngreso = ipUltimoIngreso;
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
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", rol='" + rol + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaUltimoIngreso=" + fechaUltimoIngreso +
                ", ipUltimoIngreso='" + ipUltimoIngreso + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", cliente=" + cliente +
                '}';
    }
}
