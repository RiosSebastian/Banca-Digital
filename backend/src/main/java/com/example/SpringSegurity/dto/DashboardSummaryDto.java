package com.example.SpringSegurity.dto;

public record DashboardSummaryDto(

        Double totalBalance,

        Double monthlyIncome,

        Double monthlyExpenses,

        Double savings

) {
}