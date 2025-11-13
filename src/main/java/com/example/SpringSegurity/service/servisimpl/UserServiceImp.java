package com.example.SpringSegurity.service.servisimpl;

import com.example.SpringSegurity.dto.dtoReq.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import com.example.SpringSegurity.entity.UserEntity;
import com.example.SpringSegurity.exceptions.NotFoundException;
import com.example.SpringSegurity.mapper.UserMapper;
import com.example.SpringSegurity.repository.UserRepository;
import com.example.SpringSegurity.service.UserService;
import com.example.SpringSegurity.util.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private UserMapper modelMapper;
    @Override
    public UserDTORes getUser(Long id) {

        return UserMapper.toDTO(userRepository.findById(id).orElseThrow(()->new RuntimeException("no se encontro usuario")));
    }

    @Override
    public UserDTORes createUsuario(UserDTOReq userDTOReq) {

        UserEntity usuario = UserMapper.toUserEntity(userDTOReq);
        usuario = userRepository.save(usuario);
        return UserMapper.toDTO(usuario);
    }

    @Override
    public UserDTORes getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El usuario con ID: " + id + "no fue encontrado"));
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTORes updateUser(Long id, UserDTOReq userDTOReq) {
        UserEntity existeUser = userRepository.findById(id)
                .orElseThrow(()-> new ChangeSetPersister.NotFoundException());
        existeUser.setNombre(userDTOReq.name());
        existeUser.setPassword(userDTOReq.password());
        existeUser.setEmail(userDTOReq.email());
        existeUser.setUserEnum(UserEnum.valueOf(userDTOReq.userEnum()));

        existeUser = userRepository.save(existeUser);
        return  UserMapper.toDTO(existeUser);
    }

    @Override
    public void deleteUser(Long id)throws ChangeSetPersister.NotFoundException  {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ChangeSetPersister.NotFoundException());
        userRepository.delete(user);
    }

    @Override
    public List<UserDTORes> getAllUser() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTORes updateProfileImage(Long id, String imageUrl) {
        return null;
    }
}
