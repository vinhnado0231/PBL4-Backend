package com.example.backend.service.impl;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.IUserService;
import com.example.backend.ultil.EncrypPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
        Account account = new Account(userCreateDTO.getUsername(), EncrypPasswordUtils.EncrypPasswordUtils(userCreateDTO.getPassword()));
        UserFavorite userFavorite = new UserFavorite();
        user.setNameUser(userCreateDTO.getName());
        user.setGenderUser(userCreateDTO.isGender());
        user.setDateOfBirthUser(userCreateDTO.getDateOfBirth());
        user.setEmailUser(userCreateDTO.getEmail());
        user.setAccount(account);
        user.setUserFavorite(userFavorite);
        userRepository.save(user);
    }

    @Override
    public LinkedList<UserDTO> getAllUserDTO() {
        LinkedList<UserDTO> userDTOs = new LinkedList<>();
        List<User> users = userRepository.findAll();
        UserDTO userDTO;
        for (User user: users) {
            userDTO = new UserDTO();
            if(user.getUserFavorite()!=null) {
                userDTO.setIdUser(user.getIdUser());
                userDTO.setGenderUser(user.isGenderUser());
                userDTO.setDateOfBirthUser(user.getDateOfBirthUser());
                userDTO.setHomeTownUser(user.getHomeTownUser());
                userDTO.setAddressUser(user.getAddressUser());
                userDTO.setCode(user.getUserFavorite().getCode());
                userDTO.setAn_uong(user.getUserFavorite().getAn_uong());
                userDTO.setXem_phim(user.getUserFavorite().getXem_phim());
                userDTO.setDoc_sach(user.getUserFavorite().getDoc_sach());
                userDTO.setThe_thao(user.getUserFavorite().getThe_thao());
                userDTO.setCa_nhac(user.getUserFavorite().getCa_nhac());
                userDTO.setDu_lich(user.getUserFavorite().getDu_lich());
                userDTO.setCoffee(user.getUserFavorite().getCoffee());
                userDTO.setChoi_game(user.getUserFavorite().getChoi_game());
                userDTO.setNau_an(user.getUserFavorite().getNau_an());
            }
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
}
