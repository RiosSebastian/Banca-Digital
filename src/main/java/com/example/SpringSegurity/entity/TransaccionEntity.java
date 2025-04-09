package com.example.SpringSegurity.entity;

import com.example.SpringSegurity.util.TipoTransaccion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipo;

    @ManyToOne
    private CuentaEntity cuentaOrigen;

    @ManyToOne
    private CuentaEntity cuentaDestino;


}
