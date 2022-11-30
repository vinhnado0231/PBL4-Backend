package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupDTO {
    public long idGroup;
    public String groupName;
    public boolean isSingle;
    public int roleGroup;
    public Long idMessage;
    public String message;
    public boolean isText;
    public String nameSender;
    public long idLastMessage;
}