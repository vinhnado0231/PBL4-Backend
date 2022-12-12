package com.example.backend.service;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;

import java.util.LinkedList;

public interface IUserService {
    User getUserByIdUser(Long idUser);
    void createNewUser(UserCreateDTO userCreateDTO);
    LinkedList<UserDTO> getAllUserDTO();
}
