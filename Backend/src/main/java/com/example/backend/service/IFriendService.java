package com.example.backend.service;


import com.example.backend.dto.FriendDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFriendService {
    void createFriendRequest(User user,long idFriend);

    void updateFriend(Friend friend);

    ArrayList<FriendDTO> getAllFriendByIdUser(long idUser);

    List<FriendDTO> getAllFriendRequestByIdUser(long idUser);

    List<FriendDTO> getAllMyFriendRequestByIdUser(long idUser);


    Friend getFriendByIdUserAndIdFriend(long idUser,long idFriend);

    void deleteFriend(Friend friend);

    List<Friend> searchFriend(long idUser,String search);

    boolean isFriend(long idUser, long idFriend);

    List<FriendDTO> getListFriendRecommend(long idUSer) throws InterruptedException, ExecutionException;

    long getMutualFriend(long idUser1, long idUser2);

    List<FriendDTO> getSearchListFriendRecommend(String search, long idUserByUsername) throws ExecutionException, InterruptedException;
}
