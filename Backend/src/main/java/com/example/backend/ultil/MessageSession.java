package com.example.backend.ultil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageSession {
    private long idUser;
    private long idGroup;
    private long idMessage;
}

