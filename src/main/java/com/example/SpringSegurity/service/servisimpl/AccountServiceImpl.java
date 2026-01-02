package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.AccountDtoRes;
import com.example.SpringSegurity.dto.dtoReq.AccountDtoReq;
import com.example.SpringSegurity.entity.AccountEntity;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.mapper.AccountMapper;
import com.example.SpringSegurity.repository.AccountRepository;
import com.example.SpringSegurity.repository.UserRepository;
import com.example.SpringSegurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDtoRes createAccount(AccountDtoReq dto) {
        UserEntity user = userRepository.findById(dto.user().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccountEntity account = new AccountEntity();
        account.setUser(user);
        account.setBalance(0.0);

        // Esto llama al @PrePersist (alias + CBU)
        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    @Override
    public AccountDtoRes updateAccount(Long id, AccountDtoReq dto) {
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setAlias(dto.alias());
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDtoRes> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .toList();
    }
}

