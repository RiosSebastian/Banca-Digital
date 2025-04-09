package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.mapper.UserMapper;
import com.example.SpringSegurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserDTORes getUser(Long id) {

        return UserMapper.toDTO(userRepository.findById(id).orElseThrow(()->new RuntimeException("no se encontro usuario")));
    }

    @Override
    public UserDTORes createUsuario(UserDTOReq userDTOReq) {
        return null;
    }

    @Override
    public UserDTORes getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTORes updateUser(Long id, UserDTOReq userDTOReq) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public List<UserDTORes> getAllUser() {
        return null;
    }

    @Override
    public UserDTORes updateProfileImage(Long id, String imageUrl) {
        return null;
    }
}
