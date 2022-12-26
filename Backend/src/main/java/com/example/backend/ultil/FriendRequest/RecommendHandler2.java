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
public class RecommendHandler2 {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserService userService;

    @Async
    public CompletableFuture<List<FriendDTO>> getData(int idUSer) {
        List<UserDTO> listFriendRecommend2 = FriendRecommend2.ListRecommend(idUSer);
        List<FriendDTO> result2 = new ArrayList<>();
        for (UserDTO userDTO : listFriendRecommend2) {
            User user = userService.getUserByIdUser(userDTO.getIdUser());
            System.out.println("Luồng 2: id_user= " +user.getIdUser());
            result2.add(new FriendDTO(user.getIdUser(), user.getNameUser(), user.getAvatar(), (int) friendService.getMutualFriend(idUSer, user.getIdUser()),null));
        }
        return new AsyncResult<>(result2).completable();
    }
}
