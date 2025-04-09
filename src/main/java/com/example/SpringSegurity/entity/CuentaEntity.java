package com.example.SpringSegurity.entity;

import com.example.SpringSegurity.util.Estado;
import com.example.SpringSegurity.util.TipoTransaccion;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private  String numero;
  private TipoTransaccion tipo;
  private Double saldo;
  private Estado estado;
  private UserEntity usuario;


}
