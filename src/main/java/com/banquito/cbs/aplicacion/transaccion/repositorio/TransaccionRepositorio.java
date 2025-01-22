package com.banquito.cbs.aplicacion.transaccion.repositorio;

import com.banquito.cbs.aplicacion.transaccion.modelo.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransaccionRepositorio extends JpaRepository<Transaccion, Integer> {
    List<Transaccion> findByCuentaId(Integer cuentaId);
    List<Transaccion> findByTarjetaId(Integer tarjetaId);

    @Query("SELECT t FROM Transaccion t JOIN DetalleTransaccion dt ON dt.transaccionId = t.id WHERE t.tarjetaId = :tarjetaId " +
            "AND dt.descripcion = :descripcion " +
            "AND dt.cuentaDestino = :cuentaDestino " +
            "AND dt.beneficiario = :beneficiario " +
            "AND t.fechaCreacion >= :fechaLimite")
    List<Transaccion> buscarTransaccionesRecientes(@Param("tarjetaId") Integer tarjetaId,
                                                   @Param("descripcion") String descripcion,
                                                   @Param("cuentaDestino") String cuentaDestino,
                                                   @Param("beneficiario") String beneficiario,
                                                   @Param("fechaLimite") LocalDateTime fechaLimite);
}
