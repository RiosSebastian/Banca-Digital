package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.CuentaEntity;

public class CuentaMapper {

    public static UserDTORes toDTO(CuentaEntity cuentaEntity){
        return new UserDTORes(cuentaEntity.getId(), cuentaEntity.getNumero(),cuentaEntity.getTipo().toString(),cuentaEntity.getEstado().toString());
    }
}
