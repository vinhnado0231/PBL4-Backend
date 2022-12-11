package com.example.backend.controller;

import com.example.backend.model.Friend;
import com.example.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/send-friend-request")
    public ResponseEntity<Object> sendFriendRequest(@RequestParam Long idFriend, @RequestParam boolean request, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        if (request) {
            friendService.createFriendRequest(userService.getUserByIdUser(idUser), idFriend);
            friendService.createFriendRequest(userService.getUserByIdUser(idFriend), idUser);
        } else {
            friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idUser, idFriend));
            friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idFriend, idUser));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reply-friend-request")
    public ResponseEntity<Object> replyFriendRequest(@RequestParam Long idFriend, @RequestParam boolean reply, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        Friend friend;
        if (reply) {
            friend = friendService.getFriendByIdFriendAndIdUser(idUser, idFriend);
            friend.setIsrequest(false);
            friendService.updateFriend(friend);
            friend = friendService.getFriendByIdFriendAndIdUser(idFriend, idUser);
            friend.setIsrequest(false);
            friendService.updateFriend(friend);
        } else {
            friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idUser, idFriend));
            friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idFriend, idUser));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete-friend")
    public ResponseEntity<Object> deleteFriend(@RequestParam Long idFriend, Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idUser, idFriend));
        friendService.deleteFriend(friendService.getFriendByIdFriendAndIdUser(idFriend, idUser));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-all-friend")
    public ResponseEntity<List<Friend>> getllFriend(Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<Friend> friends = friendService.getAllFriendByIdUser(idUser);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/get-all-friend-request")
    public ResponseEntity<List<Friend>> getllFriendRequest(Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<Friend> friends = friendService.getAllFriendRequestByIdUser(idUser);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
    @GetMapping("/search-friend")
    public ResponseEntity<List<Friend>> searchFriend(String search,Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        List<Friend> friends = friendService.getAllFriendRequestByIdUser(idUser);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
}
