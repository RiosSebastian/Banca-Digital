package com.example.SpringSegurity.auth.service;

import com.example.SpringSegurity.auth.dto.request.LoginRequest;
import com.example.SpringSegurity.auth.dto.request.RegisterRequest;
import com.example.SpringSegurity.auth.dto.response.LoginResponse;
import com.example.SpringSegurity.dto.UserDTORes;


public interface AuthService {

    LoginResponse login(LoginRequest request);

    UserDTORes register(RegisterRequest request);
}
