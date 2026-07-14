package com.example.SpringSegurity.repository;

import com.example.SpringSegurity.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
    List<AccountEntity> findByUserId(Long userId);
}
