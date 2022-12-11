package com.example.backend.service;


import com.example.backend.model.Friend;
import com.example.backend.model.User;

import java.util.List;

public interface IFriendService {
    void createFriendRequest(User user,long idFriend);

    void updateFriend(Friend friend);

    List<Friend> getAllFriendByIdUser(long idUser);

    List<Friend> getAllFriendRequestByIdUser(long idUser);


    Friend getFriendByIdFriendAndIdUser(long idUser,long idFriend);

    void deleteFriend(Friend friend);
}
