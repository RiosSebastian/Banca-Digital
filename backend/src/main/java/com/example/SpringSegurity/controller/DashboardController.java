package com.example.SpringSegurity.controller;

import com.example.SpringSegurity.dto.DashboardDtoRes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardDtoRes getDashboard(Authentication authentication){

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return dashboardService.getDashboard(user.getId());

    }

}