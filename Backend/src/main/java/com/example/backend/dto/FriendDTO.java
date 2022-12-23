package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDTO {
    private long idFriend;
    private String name;
    private String avatar;
    private Integer mutualFriends;
}
