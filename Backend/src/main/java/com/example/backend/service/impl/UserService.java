package com.example.backend.service.impl;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.model.Account;
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

    @Override
    public void createNewUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        Account account = new Account(userCreateDTO.getUsername(),userCreateDTO.getPassword());
        user.setNameUser(userCreateDTO.getName());
        user.setGenderUser(userCreateDTO.isGender());
        user.setDateOfBirthUser(userCreateDTO.getDateOfBirth());
        user.setEmailUser(userCreateDTO.getEmail());
        user.setAccount(account);
        userRepository.save(user);
    }
}
