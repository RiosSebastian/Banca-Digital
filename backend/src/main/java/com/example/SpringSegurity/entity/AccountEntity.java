package com.example.SpringSegurity.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
    public class AccountEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String alias;
        private String cbu;
        private Double balance = 0.0;

        @OneToOne
        private UserEntity user;

    @OneToMany(mappedBy = "account")
    private List<TransaccionEntity> transacciones;

    @PrePersist
    public void generateAliasAndCBU() {
        // Verificación de seguridad
        String nombre = (user != null && user.getNombre() != null) ? user.getNombre() : "usuario";
        String email = (user != null && user.getEmail() != null) ? user.getEmail() : "anonimo";

        this.alias = generateAlias(nombre, email);
        this.cbu = generateCBU();
    }

    private String generateAlias(String name, String email) {
        String base = name.toLowerCase().replaceAll("\\s+", ".") +
                "." + UUID.randomUUID().toString().substring(0, 5);
        return base;
    }

    private String generateCBU() {
        return String.format("%022d", Math.abs(new Random().nextLong()) % 1_000_000_000_000_000_000L);
    }

    }


