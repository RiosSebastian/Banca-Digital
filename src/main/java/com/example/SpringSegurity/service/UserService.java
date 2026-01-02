package com.example.SpringSegurity.service;

import com.example.SpringSegurity.dto.dtoReq.UserDTOReq;
import com.example.SpringSegurity.dto.UserDTORes;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    public UserDTORes getUser(Long id);

    UserDTORes createUser(UserDTOReq userDTOReq);

    // Firma del método para obtener un usuario por su ID
    UserDTORes getUserById(Long id);

    // Firma del método para actualizar un usuario
    UserDTORes updateUser(Long id, UserDTOReq userDTOReq) throws ChangeSetPersister.NotFoundException;

    // Firma del método para eliminar un usuario por su ID
    void deleteUser(Long id) throws ChangeSetPersister.NotFoundException;

    // Firma del método para obtener todos los usuarios
    List<UserDTORes>getAllUser();
    UserDTORes updateProfileImage(Long id, String imageUrl);


}
