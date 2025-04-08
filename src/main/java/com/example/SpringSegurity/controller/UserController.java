package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTORes> findOneById(@PathVariable Long id) {
        UserDTORes usuario = userService.getUser(id);

        return ResponseEntity.ok(usuario);
    }
}
