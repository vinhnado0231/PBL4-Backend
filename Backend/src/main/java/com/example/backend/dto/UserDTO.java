package com.example.backend.dto;

import com.example.backend.model.Account;
import com.example.backend.model.Friend;
import com.example.backend.model.UserFavorite;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class UserDTO {
    private long idUser;
    private String nameUser;
    private boolean genderUser;
    private String dateOfBirthUser;
    private String nickname;
    private String homeTownUser;
}
