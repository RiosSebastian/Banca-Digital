package com.example.SpringSegurity.repository;

import com.example.SpringSegurity.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {
}
