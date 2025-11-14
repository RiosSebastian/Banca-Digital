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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransaccionEntity> transacciones = new ArrayList<>();

    @PrePersist
        public void generateAliasAndCBU() {
            this.alias = generateAlias(user.getNombre(), user.getEmail());
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


