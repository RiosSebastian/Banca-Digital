package com.example.SpringSegurity.repository;

import com.example.SpringSegurity.entity.TransaccionEntity;
import com.example.SpringSegurity.util.TipoTransaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {

    @Query("""
           SELECT COALESCE(SUM(t.monto), 0)
           FROM TransaccionEntity t
           WHERE t.cuentaOrigen.id = :accountId
           AND t.tipo = com.example.SpringSegurity.util.TipoTransaccion.TRANSFERENCIA_ENVIADA
           AND DATE(t.fecha) = CURRENT_DATE
           """)
    Double sumTransfersToday(Long accountId);

    List<TransaccionEntity> findByCuentaOrigenId(Long accountId);

    List<TransaccionEntity> findByCuentaDestinoId(Long accountId);

    List<TransaccionEntity> findTop5ByAccountUserIdOrderByFechaDesc(Long userId);//Últimas transacciones

    @Query("""
            SELECT COALESCE(SUM(t.monto),0)
            FROM TransaccionEntity t
            WHERE t.account.user.id = :userId
            AND (
                    t.tipo = com.example.SpringSegurity.util.TipoTransaccion.DEPOSITO
                     OR
                    t.tipo = com.example.SpringSegurity.util.TipoTransaccion.TRANSFERENCIA_RECIBIDA
                )
            AND YEAR(t.fecha)=YEAR(CURRENT_DATE)
            AND MONTH(t.fecha)=MONTH(CURRENT_DATE)
            """)
    Double sumMonthlyIncome(Long userId);//Ingresos del mes

    @Query("""
            SELECT COALESCE(SUM(t.monto),0)
            FROM TransaccionEntity t
            WHERE t.account.user.id = :userId
            AND (
                    t.tipo = com.example.SpringSegurity.util.TipoTransaccion.RETIRO
                     OR
                    t.tipo = com.example.SpringSegurity.util.TipoTransaccion.TRANSFERENCIA_ENVIADA
                )
            AND YEAR(t.fecha)=YEAR(CURRENT_DATE)
            AND MONTH(t.fecha)=MONTH(CURRENT_DATE)
           """)
    Double sumMonthlyExpenses(Long userId);

    List<TransaccionEntity> findByAccountUserIdOrderByFechaAsc(Long userId);

    @Query("""
    SELECT t FROM TransaccionEntity t
    WHERE t.account.id = :cuentaId
       OR t.cuentaOrigen.id = :cuentaId
       OR t.cuentaDestino.id = :cuentaId
    ORDER BY t.fecha DESC
    """)
    List<TransaccionEntity> findAllByCuentaId(Long cuentaId);

    @Query("""
    SELECT t FROM TransaccionEntity t
    WHERE t.account.user.id = :userId
       OR t.cuentaOrigen.user.id = :userId
       OR t.cuentaDestino.user.id = :userId
    ORDER BY t.fecha DESC
    """)
    Page<TransaccionEntity> findAllByUsuario(Long userId, Pageable pageable);

    @Query("""
    SELECT t FROM TransaccionEntity t
    WHERE (t.account.user.id = :userId
       OR t.cuentaOrigen.user.id = :userId
       OR t.cuentaDestino.user.id = :userId)
       AND t.tipo IN :tipos
    ORDER BY t.fecha DESC
    """)
    Page<TransaccionEntity> findAllByUsuarioAndTipos(Long userId, List<TipoTransaccion> tipos, Pageable pageable);
}