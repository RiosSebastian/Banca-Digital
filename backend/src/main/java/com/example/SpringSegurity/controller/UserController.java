package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.ApiResponse;
import com.example.SpringSegurity.dto.dtoReq.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.exceptions.UnauthorizedException;
import com.example.SpringSegurity.repository.AccountRepository;
import com.example.SpringSegurity.repository.UserRepository;
import com.example.SpringSegurity.service.UserService;
import com.example.SpringSegurity.util.ResponseBuilder;
import com.example.SpringSegurity.util.UserEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")

public class UserController {
    private final UserService userService;


    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTORes>> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        "Usuario encontrado",
                        userService.getUser(id)
                )
        );
    }


    // Crear un nuevo usuario
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTORes> createUser(@Valid @RequestBody UserDTOReq userDTOReq) {
        UserDTORes createdUser = userService.createUser(userDTOReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    //actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserDTORes> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTOReq userDTOReq,
            Authentication authentication) throws ChangeSetPersister.NotFoundException {

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        boolean isAdmin = currentUser.getUserEnum() == UserEnum.ADMIN;

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new UnauthorizedException("No podés editar otro usuario");
        }

        UserDTORes updatedUser = userService.updateUser(id, userDTOReq, isAdmin);
        return ResponseEntity.ok(updatedUser);
    }

    // Eliminar un usuario por ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
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
