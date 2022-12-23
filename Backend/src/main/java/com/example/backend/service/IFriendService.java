package com.example.backend.service;


import com.example.backend.dto.FriendDTO;
import com.example.backend.dto.FriendStatusDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IFriendService {
    void createFriendRequest(User user,long idFriend);

    void updateFriend(Friend friend);

    ArrayList<FriendDTO> getAllFriendByIdUser(long idUser);

    List<Friend> getAllFriendRequestByIdUser(long idUser);


    Friend getFriendByIdFriendAndIdUser(long idUser,long idFriend);

    void deleteFriend(Friend friend);

    List<Friend> searchFriend(long idUser,String search);

    List<FriendStatusDTO> getStatusFriend(long idUserByUsername);

    boolean isFriend(long idUser,long idFriend);

    List<FriendDTO> getListFriendRequest(long idUSer);
}
