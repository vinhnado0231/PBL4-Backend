package com.example.backend.service.impl;

import com.example.backend.model.Friend;
import com.example.backend.repository.IFriendRepository;
import com.example.backend.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private IFriendRepository friendRepository;
    @Override
    public void createFriendRequest(Friend friend) {
        friendRepository.save(friend);
    }

    @Override
    public void updateFriend(Friend friend) {
        friendRepository.save(friend);
    }

    @Override
    public List<Friend> getAllFriendByIdUser(long idUser) {
        return friendRepository.findAllByIdUser(idUser);
    }

    @Override
    public List<Friend> getAllFriendRequestByIdUser(long idUser) {
        return friendRepository.findAllByIdFriend(idUser);
    }
}
