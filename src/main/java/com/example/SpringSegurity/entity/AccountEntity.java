package com.example.SpringSegurity.entity;


import jakarta.persistence.*;

import java.util.Random;

@Entity
    public class AccountEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String alias;
        private String cbu;
        private Double balance = 0.0;

        @OneToOne
        private UserEntity user;

        @PrePersist
        public void generateAliasAndCBU() {
            this.alias = generateAlias(user.getNombre(), user.getEmail());
            this.cbu = generateCBU();
        }

        private String generateAlias(String name, String email) {
            String base = name.toLowerCase().replaceAll("\\s+", ".") +
                    "." + (int)(Math.random() * 100);
            return base;
        }

        private String generateCBU() {
            return String.format("%022d", new Random().nextLong() & Long.MAX_VALUE);
        }
    }


