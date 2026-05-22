package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.TransaccionDtoRes;
import com.example.SpringSegurity.entity.TransaccionEntity;

public class TransaccionMapper {

    public static TransaccionDtoRes toDto(TransaccionEntity transaccionEntity){
        return new TransaccionDtoRes( transaccionEntity.getFecha(),transaccionEntity.getTipo() );
    }
}
