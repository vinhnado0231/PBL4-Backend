package com.example.backend.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String email;
    private String name;
    private String dateOfBirth;
    private boolean gender;
}
