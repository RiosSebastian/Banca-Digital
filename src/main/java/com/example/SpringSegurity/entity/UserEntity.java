package com.example.SpringSegurity.entity;

import com.example.SpringSegurity.util.UserEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false)
    private String password;


    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserEnum userEnum;

   private LocalDateTime  fechaRegistro;


}
