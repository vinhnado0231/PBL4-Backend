package com.example.backend.service.impl;

import com.example.backend.dto.FriendDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.User;
import com.example.backend.repository.IFriendRepository;
import com.example.backend.service.IAccountService;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserService;
import com.example.backend.ultil.FriendRequest.FriendRecommendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private IFriendRepository friendRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;
    @Autowired
    private FriendRecommendResult friendRecommendResult;

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
    public ArrayList<FriendDTO> getAllFriendByIdUser(long idUser) {
        List<Friend> friends = friendRepository.findAllFriendByIdUser(idUser);
        return (ArrayList<FriendDTO>) changeFriendToFriendDTO(friends,idUser);
    }

    @Override
    public List<FriendDTO> getAllFriendRequestByIdUser(long idUser) {
        List<Friend> friends = friendRepository.findAllFriendRequestByIdUser(idUser);
        return changeFriendToFriendDTO(friends,idUser);
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
        return friendRepository.searchFriend(idUser, search);
    }

    @Override
    public boolean isFriend(long idUser, long idFriend) {
        return friendRepository.isFriend(idUser, idFriend) != null;
    }

    @Override
    public long getMutualFriend(long idUser1, long idUser2) {
        return friendRepository.mutualFriend(idUser1, idUser2);
    }

    @Override
    public List<FriendDTO> getListFriendRecommend(long idUSer) throws InterruptedException, ExecutionException {
        return friendRecommendResult.getListRecommendFriend((int) idUSer);
    }

    public List<FriendDTO> changeFriendToFriendDTO(List<Friend> friends,long idUser) {
        ArrayList<FriendDTO> result = new ArrayList<>();
        for (Friend friend : friends) {
            User user = userService.getUserByIdUser(friend.getIdFriend());
            result.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) getMutualFriend(idUser, friend.getIdFriend()), accountService.checkStatusByIdUser(friend.getIdFriend())));
        }
        return result;
    }
}
