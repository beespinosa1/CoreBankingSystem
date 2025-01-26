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
    private LocalDateTime fechaEmision;

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
                ", cupoAprobado=" + cupoAprobado +
                ", cupoDisponible=" + cupoDisponible +
                ", diaCorte=" + diaCorte +
                ", fechaEmision=" + fechaEmision +
                ", estado='" + estado + '\'' +
                '}';
    }
}
