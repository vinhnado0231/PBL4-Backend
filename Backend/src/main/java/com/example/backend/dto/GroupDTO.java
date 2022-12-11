package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GroupDTO {
    public long idGroup;
    public String groupName;
    public boolean isSingle;
    public int roleGroup;
    public Long idMessage;
    public String message;
    public LocalDateTime time;
    public Integer type;
    public Long idSender;
    public String nameSender;
    public long idLastMessage;
}