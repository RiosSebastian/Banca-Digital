package com.example.SpringSegurity.mapper;

import com.example.SpringSegurity.dto.dtoReq.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.util.UserEnum;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTORes toDTO(UserEntity user) {
        return new UserDTORes(
                user.getId(),
                user.getNombre(),
                user.getEmail(),
                user.getUserEnum().name()
        );
    }

    public UserEntity toEntity(UserDTOReq dto) {
        return UserEntity.builder()
                .nombre(dto.name())
                .email(dto.email())
                .userEnum(UserEnum.valueOf(dto.userEnum()))
                .build();
    }
}
