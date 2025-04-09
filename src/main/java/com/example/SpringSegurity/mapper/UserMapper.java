package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;

public class UserMapper {
    public static UserDTORes toDTO(UserEntity userEntity){
        return new UserDTORes(userEntity.getId(), userEntity.getNombre(), userEntity.getEmail(),userEntity.getUserEnum().toString());
    }

}
