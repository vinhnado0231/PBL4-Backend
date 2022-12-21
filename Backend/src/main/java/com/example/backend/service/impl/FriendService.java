package com.example.backend.service.impl;

import com.example.backend.dto.FriendStatusDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.User;
import com.example.backend.repository.IFriendRepository;
import com.example.backend.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private IFriendRepository friendRepository;

    @Override
    public void createFriendRequest(User user, long idFriend) {
        Friend friend = new Friend();
        friend.setIdFriend(idFriend);
        friend.setUser(user);
        friend.setIsrequest(true);
        friendRepository.save(friend);
    }

    @Override
    public void updateFriend(Friend friend) {
        friendRepository.save(friend);
    }

    @Override
    public List<Friend> getAllFriendByIdUser(long idUser) {
        return friendRepository.findAllFriendByIdUser(idUser);
    }

    @Override
    public List<Friend> getAllFriendRequestByIdUser(long idUser) {
        return friendRepository.findAllFriendRequestByIdUser(idUser);
    }

    @Override
    public Friend getFriendByIdFriendAndIdUser(long idUser, long idFriend) {
        return friendRepository.findFriendByIdUserAndIdFriend(idUser, idFriend);
    }

    @Override
    public void deleteFriend(Friend friend) {
        friendRepository.delete(friend);
    }

    @Override
    public List<Friend> searchFriend(long idUser, String search) {
        return  friendRepository.searchFriend(idUser,search);
    }

    @Override
    public List<FriendStatusDTO> getStatusFriend(long idUserByUsername) {
        List<FriendStatusDTO> friendStatusDTOList = new ArrayList<>();

        return friendStatusDTOList;
    }

    @Override
    public boolean isFriend(long idUser, long idFriend) {
        return friendRepository.isFriend(idUser, idFriend) != null ? true : false;
    }
}
