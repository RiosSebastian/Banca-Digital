package com.example.SpringSegurity.repository;

import com.example.SpringSegurity.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;
@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity,Long> {
}
