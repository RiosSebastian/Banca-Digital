package com.example.SpringSegurity.dto;

import java.util.List;

public record DashboardDtoRes(

        Double totalBalance,

        Double monthlyIncome,

        Double monthlyExpenses,

        Double savings,

        List<BalanceHistoryDto> history,

        List<RecentTransactionDto> recentTransactions

){}