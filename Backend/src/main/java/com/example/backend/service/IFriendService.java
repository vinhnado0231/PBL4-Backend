package com.example.backend.service;


import com.example.backend.model.Friend;

import java.util.List;

public interface IFriendService {
    void createFriendRequest(Friend friend);

    void updateFriend(Friend friend);

    List<Friend> getAllFriendByIdUser(long idUser);

    List<Friend> getAllFriendRequestByIdUser(long idUser);
}
