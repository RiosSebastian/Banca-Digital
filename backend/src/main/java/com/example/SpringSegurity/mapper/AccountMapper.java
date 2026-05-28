package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.AccountDtoRes;
import com.example.SpringSegurity.dto.dtoReq.AccountDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDtoRes toDto(AccountEntity account) {
        return AccountDtoRes.builder()
                .alias(account.getAlias())
                .cbu(account.getCbu())
                .balance(account.getBalance())
                .userId(account.getUser().getId()) // SOLO ID
                .build();
    }
}