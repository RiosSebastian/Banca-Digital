package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.DashboardDtoRes;

public interface DashboardService {

    DashboardDtoRes getDashboard(Long userId);

}