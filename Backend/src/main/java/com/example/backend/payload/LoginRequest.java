package com.example.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private static final long serialVersionUID = 5926468583005150707L;
    private String username;
    private String password;
}
