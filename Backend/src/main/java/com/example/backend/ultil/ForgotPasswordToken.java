package com.example.backend.ultil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ForgotPasswordToken {
    private String token;
    private LocalDateTime time;
}