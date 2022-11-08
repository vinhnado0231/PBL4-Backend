package com.example.backend.service.impl;

import com.example.backend.model.User;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public User getUserByIdUser(Long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }
}
