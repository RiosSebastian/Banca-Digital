package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.DashboardDtoRes;
import com.example.SpringSegurity.dto.DashboardSummaryDto;
import com.example.SpringSegurity.service.AccountService;
import com.example.SpringSegurity.service.DashboardService;
import com.example.SpringSegurity.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final AccountService accountService;
    private final TransaccionService transactionService;

    @Override
    public DashboardDtoRes getDashboard(Long userId) {
        Double totalBalance = accountService.getTotalBalance(userId);
        Double income = transactionService.getMonthlyIncome(userId);
        Double expenses = transactionService.getMonthlyExpenses(userId);

        return new DashboardDtoRes(
                totalBalance,
                income,
                expenses,
                totalBalance - expenses,
                transactionService.getBalanceHistory(userId),
                transactionService.getRecentTransactions(userId)
        );
    }
}