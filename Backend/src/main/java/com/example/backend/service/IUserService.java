package com.example.backend.service;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.model.User;

public interface IUserService {
    User getUserByIdUser(Long idUser);
    void createNewUser(UserCreateDTO userCreateDTO);
}
