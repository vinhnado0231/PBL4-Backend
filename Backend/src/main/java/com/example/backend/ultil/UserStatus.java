package com.example.backend.ultil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserStatus {
    private long idUser;
    private LocalDateTime time;
}
