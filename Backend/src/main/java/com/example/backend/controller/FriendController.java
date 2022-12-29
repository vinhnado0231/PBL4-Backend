package com.example.backend.controller;

import com.example.backend.dto.FriendDTO;
import com.example.backend.model.Friend;
import com.example.backend.model.Group;
import com.example.backend.model.User;
import com.example.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/friend")
public class FriendController {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IGroupUserService groupUserService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IUserFavoriteService userFavoriteService;

    @GetMapping("/send-friend-request")
    public ResponseEntity<Object> sendFriendRequest(@RequestParam Long idFriend, @RequestParam boolean request, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        if (request) {
            if (friendService.getFriendByIdUserAndIdFriend(idUser, idFriend) != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            friendService.createFriendRequest(userService.getUserByIdUser(idUser), idFriend);
        } else {
            friendService.deleteFriend(friendService.getFriendByIdUserAndIdFriend(idUser, idFriend));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reply-friend-request")
    public ResponseEntity<Object> replyFriendRequest(@RequestParam Long idFriend, @RequestParam boolean reply, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        Friend friend;
        if (reply) {
            friend = friendService.getFriendByIdUserAndIdFriend(idFriend, idUser);
            friend.setIsrequest(false);
            friendService.updateFriend(friend);

            friendService.createFriendRequest(userService.getUserByIdUser(idUser), idFriend);
            friend = friendService.getFriendByIdUserAndIdFriend(idUser, idFriend);
            friend.setIsrequest(false);
            friendService.updateFriend(friend);

            Group group = new Group();
            group.setSingle(true);
            groupService.saveGroup(group);
            List<User> users = new ArrayList<>();
            users.add(userService.getUserByIdUser(idUser));
            users.add(userService.getUserByIdUser(idFriend));
            groupUserService.addUserToGroup(users, group, null);

            userFavoriteService.ChangeFavoriteScoreByIdUser(idUser);
            userFavoriteService.ChangeFavoriteScoreByIdUser(idFriend);
        } else {
//            friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idUser, idFriend));
            friendService.deleteFriend(friendService.getFriendByIdUserAndIdFriend(idFriend, idUser));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-friend")
    public ResponseEntity<Object> deleteFriend(@RequestParam Long idFriend, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        friendService.deleteFriend(friendService.getFriendByIdUserAndIdFriend(idUser, idFriend));
        friendService.deleteFriend(friendService.getFriendByIdUserAndIdFriend(idFriend, idUser));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-all-friend")
    public ResponseEntity<ArrayList<FriendDTO>> getAllFriend(@RequestParam(required = false) Long idUser, Authentication authentication) {
        if (idUser == null) {
            idUser = accountService.getIdUserByUsername(authentication.getName());
            ArrayList<FriendDTO> friends = friendService.getAllFriendByIdUser(idUser);
            return new ResponseEntity<>(friends, HttpStatus.OK);
        }
        ArrayList<FriendDTO> friends = friendService.getAllFriendByIdUser(idUser,accountService.getIdUserByUsername(authentication.getName()));
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/get-all-friend-request")
    public ResponseEntity<List<FriendDTO>> getAllFriendRequest(Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<FriendDTO> friends = friendService.getAllFriendRequestByIdUser(idUser);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/get-all-my-friend-request")
    public ResponseEntity<List<FriendDTO>> getAllMyFriendRequest(Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<FriendDTO> friends = friendService.getAllMyFriendRequestByIdUser(idUser);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/search-friend")
    public ResponseEntity<List<Friend>> searchFriend(String search, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<Friend> friends = friendService.searchFriend(idUser, search);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/recommend-friend")
    public ResponseEntity<Object> RecommendHandler(Authentication authentication) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(friendService.getListFriendRecommend(accountService.getIdUserByUsername(authentication.getName())), HttpStatus.OK);
    }

    @GetMapping("/search-recommend-friend")
    public ResponseEntity<Object> RecommendHandler(@RequestParam String search, Authentication authentication) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(friendService.getSearchListFriendRecommend(search, accountService.getIdUserByUsername(authentication.getName())), HttpStatus.OK);
    }
}
