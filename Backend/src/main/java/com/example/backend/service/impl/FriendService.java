package com.example.backend.service.impl;

import com.example.backend.dto.FriendDTO;
import com.example.backend.dto.FriendStatusDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.User;
import com.example.backend.repository.IFriendRepository;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserService;
import com.example.backend.ultil.FriendRequest.FriendRecommend1;
import com.example.backend.ultil.FriendRequest.FriendRecommend2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private IFriendRepository friendRepository;

    @Autowired
    private IUserService userService;

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
        ArrayList<FriendDTO> result = new ArrayList<>();
        for (Friend friend : friends) {
            User user = userService.getUserByIdUser(friend.getIdFriend());
            result.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), null));
        }
        return result;
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
        return friendRepository.searchFriend(idUser, search);
    }

    @Override
    public List<FriendStatusDTO> getStatusFriend(long idUserByUsername) {
        List<FriendStatusDTO> friendStatusDTOList = new ArrayList<>();

        return friendStatusDTOList;
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
    public List<FriendDTO> getListFriendRecommend(long idUSer) {
        List<FriendDTO> result = new ArrayList<>();
        List<UserDTO> listFriendRecommend1 = FriendRecommend1.getListRecommendAfterFilter((int) idUSer);
        List<FriendDTO> result1 = new ArrayList<>();
        for (UserDTO userDTO : listFriendRecommend1) {
            User user = userService.getUserByIdUser(userDTO.getIdUser());
            result1.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) friendRepository.mutualFriend(idUSer, user.getIdUser())));
        }
        List<UserDTO> listFriendRecommend2 = FriendRecommend2.ListRecommend((int) idUSer);
        List<FriendDTO> result2 = new ArrayList<>();
        for (int i = 0; i < listFriendRecommend2.size(); i++) {
            User user = userService.getUserByIdUser(listFriendRecommend1.get(i).getIdUser());
            result2.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) friendRepository.mutualFriend(idUSer, user.getIdUser())));
        }

//        for (int i = 0; i < result1.size(); i++) {
//            for (int j = 0; i < result2.size(); j++) {
//                if(result1.get(i).getIdFriend()==(result2.get(j).getIdFriend())){
//                    User user = userService.getUserByIdUser(result2.get(j).getIdFriend());
//                    result.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) friendRepository.mutualFriend(idUSer, user.getIdUser())));
//                }
//            }
//        }
        for (FriendDTO friendDTO : result1) {
            if (!result.contains(friendDTO)) {
                result.add(friendDTO);
            }
        }
//        for (int i = 0; i < result2.size(); i++) {
//            if(!result.contains(result2.get(i))){
//                result.add(result2.get(i));
//            }
//        }
        return result.stream().distinct().toList();

    }
}
