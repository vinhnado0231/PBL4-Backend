package com.example.backend.controller;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.service.impl.AccountService;
import com.example.backend.service.impl.UserService;
import com.example.backend.ultil.FriendRequest.FriendRecommend1;
import com.example.backend.ultil.FriendRequest.FriendRecommend2;
import com.example.backend.ultil.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/check-valid-username")
    public ResponseEntity<Object> checkUsername(@RequestParam String username) {
        if (accountService.getAccountByUsername(username) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-valid-email")
    public ResponseEntity<Object> checkEmail(@RequestParam String email) {
        if (accountService.findAccountByEmail(email) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-valid-online")
    public ResponseEntity<Object> checkonline() {
        return  new ResponseEntity<>(FriendRecommend1.getListRecommendByIdUser(1),HttpStatus.OK);
    }

//    @Autowired
//    private IUserRepository userRepository;
    @GetMapping("/check-valid-acc")
    public ResponseEntity<Object> checkonline1() {
        LinkedList<UserDTO> list = FriendRecommend2.ListRecommend(1);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/create-new-user")
    public ResponseEntity<Object> createUser(@RequestBody UserCreateDTO user) {
        if (accountService.getAccountByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (accountService.findAccountByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
