package com.example.backend.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    String username;
    String password;
    String email;
    String name;
    String dateOfBirth;
    boolean gender;
}
