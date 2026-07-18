package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.AccountDtoRes;
import com.example.SpringSegurity.dto.dtoReq.AccountDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    // AccountMapper
    public AccountDtoRes toDto(AccountEntity account) {
        return AccountDtoRes.builder()
                .id(account.getId())
                .alias(account.getAlias())
                .cbu(account.getCbu())
                .balance(account.getBalance())
                .userId(account.getUser().getId())
                .build();
    }
}