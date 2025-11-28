package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.dtoReq.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.repository.AccountRepository;
import com.example.SpringSegurity.repository.UserRepository;
import com.example.SpringSegurity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTORes> findOneById(@PathVariable Long id) {
        UserDTORes usuario = userService.getUser(id);

        return ResponseEntity.ok(usuario);
    }

    //crear usuario
    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTORes> createUser(@Valid @RequestBody UserDTOReq userDTOReq) {
        System.out.println(userDTOReq);
        UserDTORes createdUser = userService.createUser(userDTOReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    //actualizar libro
    @PutMapping
    public ResponseEntity<UserDTORes>updateUser(@PathVariable Long id, @Valid @RequestBody UserDTOReq userDTOReq){
        UserDTORes updatedUser = userService.updateUser(id, userDTOReq);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario por ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todos los usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTORes>> getAllUsers() {
        List<UserDTORes> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

}
