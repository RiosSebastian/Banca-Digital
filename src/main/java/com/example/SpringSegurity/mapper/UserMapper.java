package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.util.UserEnum;

public class UserMapper {
    public static UserDTORes toDTO(UserEntity userEntity){
        return new UserDTORes(userEntity.getId(), userEntity.getNombre(), userEntity.getEmail(),userEntity.getUserEnum().toString());
    }


    public static UserEntity toUserEntity(UserDTOReq request) {
        UserEnum userEnum = UserEnum.valueOf(request.userEnum());
        return UserEntity.builder()
                .nombre(request.name())
                .email(request.email())
                .password(request.password())
                .userEnum(userEnum)
                .build();
    }
}
