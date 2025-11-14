package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.AccountDtoRes;
import com.example.SpringSegurity.dto.dtoReq.AccountDtoReq;

import java.util.List;

public interface AccountService {

    AccountDtoRes createAccount(AccountDtoReq accountDtoReq);//crear cuenta

    AccountDtoRes updateAccount(Long id,AccountDtoReq accountDtoReq );//actualizar cuenta

    void deleteAccount(Long id);//Eliminar cuenta

    List<AccountDtoRes>getAll();//listar cuentas


}
