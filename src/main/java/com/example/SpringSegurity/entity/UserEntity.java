package com.example.SpringSegurity.entity;

import com.example.SpringSegurity.util.UserEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String Name;


    @Column(nullable = false)
    private String password;

    private float balance;


    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserEnum userEnum;

}
