package com.banquito.cbs.aplicacion.transaccion.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
@Table(name = "TRANSACCION")
public class Transaccion implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaccion_seq")
    @SequenceGenerator(name = "transaccion_seq", sequenceName = "transaccion_id_seq", allocationSize = 1)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CUENTA_ID")
    private Integer cuentaId;

    @Column(name = "TARJETA_ID")
    private Integer tarjetaId;

    @NotNull
    @Column(name = "TIPO", length = 3, nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "CANAL", length = 3, nullable = false)
    private String canal;

    @NotNull
    @Column(name = "FECHA_HORA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaHora;

    @NotNull
    @Column(name = "VALOR", precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "COMISION", precision = 18, scale = 2, nullable = false)
    private BigDecimal comision;

    @NotNull
    @Column(name = "TAZA_INTERES", precision = 18, scale = 2, nullable = false)
    private BigDecimal tazaInteres;

    @NotNull
    @Column(name = "ES_DIFERIDO", nullable = false)
    private Boolean esDiferido;

    @NotNull
    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "TRANSACCION_ID", insertable = false, updatable = false)
    private DetalleTransaccion detalleTransaccion;

    public Transaccion(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", cuentaId=" + cuentaId +
                ", tarjetaId=" + tarjetaId +
                ", tipo='" + tipo + '\'' +
                ", canal='" + canal + '\'' +
                ", fechaHora=" + fechaHora +
                ", valor=" + valor +
                ", comision=" + comision +
                ", tazaInteres=" + tazaInteres +
                ", esDiferido=" + esDiferido +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
