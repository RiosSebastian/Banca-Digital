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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper; // Inyectado por RequiredArgsConstructor

    @Override
    public UserDTORes getUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el usuario"));
        return userMapper.toDTO(user); // Uso de instancia
    }
    @Override
    public List<UserDTORes> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTORes createUser(UserDTOReq dto) {
        UserEntity user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTORes getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El usuario con ID: " + id + "no fue encontrado"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTORes updateUser(Long id, UserDTOReq userDTOReq) throws ChangeSetPersister.NotFoundException {
        UserEntity existeUser = userRepository.findById(id)
                .orElseThrow(()-> new ChangeSetPersister.NotFoundException());
        existeUser.setNombre(userDTOReq.name());
        existeUser.setPassword(
                passwordEncoder.encode(userDTOReq.password())
        );
        existeUser.setEmail(userDTOReq.email());
        existeUser.setUserEnum(UserEnum.valueOf(userDTOReq.userEnum()));

        existeUser = userRepository.save(existeUser);
        return userMapper.toDTO(existeUser);
    }

    @Override
    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        userRepository.delete(user);
    }




    @Override
    public UserDTORes updateProfileImage(Long id, String imageUrl) {
        return null;
    }
}
