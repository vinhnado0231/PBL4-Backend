package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.FriendDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class RecommendHandler1 {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserService userService;
    @Async
    public CompletableFuture<List<FriendDTO>> getData(int idUSer) {
        List<UserDTO> listFriendRecommend1 = FriendRecommend1.getListRecommendAfterFilter(idUSer);
        List<FriendDTO> result1 = new ArrayList<>();
        for (UserDTO userDTO : listFriendRecommend1) {
            User user = userService.getUserByIdUser(userDTO.getIdUser());
            if(friendService.isFriend(idUSer,user.getIdUser())){
                continue;
            }
            System.out.println("Luồng 1: id_user= " +user.getIdUser());
            result1.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) friendService.getMutualFriend(idUSer, user.getIdUser())));
        }
        return new AsyncResult<>(result1).completable();
    }
}
