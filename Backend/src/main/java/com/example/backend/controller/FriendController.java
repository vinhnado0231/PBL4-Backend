package com.example.backend.controller;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Friend;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IGroupService;
import com.example.backend.service.IGroupUserService;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/send-friend-request")
    public ResponseEntity<List<Friend>> sendFriendRequest(@RequestParam Long idFriendRequest) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
