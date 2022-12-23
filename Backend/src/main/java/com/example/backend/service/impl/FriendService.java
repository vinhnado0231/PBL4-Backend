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
import java.util.LinkedList;
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
        for (Friend friend: friends) {
            User user = userService.getUserByIdUser(friend.getIdFriend());
            result.add(new FriendDTO(user.getIdUser(),user.getNameUser(),user.getAvatar(),null));
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
        return  friendRepository.searchFriend(idUser,search);
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
    public List<FriendDTO> getListFriendRequest(long idUSer) {
        List<UserDTO> listFriendRecommend1 = FriendRecommend1.getListRecommendByIdUser((int) idUSer);
        List<UserDTO> listFriendRecommend2 = FriendRecommend2.ListRecommend((int) idUSer);
        List<FriendDTO> result = new ArrayList<>();
        for(int i=0;i<listFriendRecommend1.size();i++){
            for(int j=0;j<listFriendRecommend2.size();j++){
                if(listFriendRecommend1.get(i).getIdUser()==listFriendRecommend2.get(j).getIdUser()){
                    UserDTO userDTO = listFriendRecommend1.get(i);
//                    result.add(new FriendDTO(userDTO.getIdUser(),userDTO.ge));
                }
            }
        }
        return result;

    }
}
