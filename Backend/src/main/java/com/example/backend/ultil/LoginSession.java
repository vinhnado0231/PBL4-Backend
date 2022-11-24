package com.example.backend.ultil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginSession {
    private long idUser;
    private String token;
    private LocalDateTime time;
}
