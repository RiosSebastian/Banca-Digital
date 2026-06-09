package com.example.SpringSegurity.repository;

import com.example.SpringSegurity.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository
        extends JpaRepository<TransaccionEntity, Long> {

    @Query("""
           SELECT COALESCE(SUM(t.monto), 0)
           FROM TransaccionEntity t
           WHERE t.cuentaOrigen.id = :accountId
           AND t.tipo = 'TRANSFERENCIA'
           AND DATE(t.fecha) = CURRENT_DATE
           """)
    Double sumTransfersToday(Long accountId);

    List<TransaccionEntity>
    findByCuentaOrigenId(Long accountId);

    List<TransaccionEntity>
    findByCuentaDestinoId(Long accountId);
}