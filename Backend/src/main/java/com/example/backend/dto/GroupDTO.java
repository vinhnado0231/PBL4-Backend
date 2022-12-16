package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GroupDTO {
    private long idGroup;
    private String groupName;
    private boolean isSingle;
    private int roleGroup;
    private Long idLastMessage;
    private String message;
    private LocalDateTime time;
    private Integer type;
    private Long idSender;
    private String nameSender;
    private Long idReadMessage;
}