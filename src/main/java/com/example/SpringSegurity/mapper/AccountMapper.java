package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.AccountDtoRes;
import com.example.SpringSegurity.dto.dtoReq.AccountDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;

public class AccountMapper {
    public  AccountDtoRes toDto(AccountEntity accountEntity){
        return AccountDtoRes.builder()
                .user(accountEntity.getUser())
                .cbu(accountEntity.getCbu())
                .alias(accountEntity.getAlias())
                .balance(accountEntity.getBalance())
                .build();
    }

    public static AccountEntity toAccountEntity(AccountDtoReq accountDtoReq){
        return AccountEntity.builder()
                .alias(accountDtoReq.alias())
                .user(accountDtoReq.user())
                .balance(accountDtoReq.balance())
                .cbu(accountDtoReq.cbu())
                .build();
    }
}
