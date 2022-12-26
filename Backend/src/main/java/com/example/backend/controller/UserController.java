package com.example.backend.controller;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;
import com.example.backend.service.impl.AccountService;
import com.example.backend.service.impl.FriendService;
import com.example.backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FriendService friendService;

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

//    @GetMapping("/check-valid-online")
//    public ResponseEntity<Object> checkonline() {
//        return  new ResponseEntity<>(FriendRecommend1.getListRecommendByIdUser(1),HttpStatus.OK);
//    }

//    @Autowired
//    private IUserRepository userRepository;
//    @GetMapping("/check-valid-acc")
//    public ResponseEntity<Object> checkonline1() {
//        LinkedList<UserDTO> list = FriendRecommend2.ListRecommend(1);
//        return new ResponseEntity<>(list,HttpStatus.OK);
//    }

    @GetMapping("/get-info-user")
    public ResponseEntity<Object> checkEmail(@RequestParam long idUser, Authentication authentication) {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            User user = userService.getUserByIdUser(idUser);
            user.setAccount(null);
            user.setEmailUser(null);
            user.setPhoneUser(null);
            map.put("user",user);
            long mutualFriend = friendService.getMutualFriend(idUser,accountService.getIdUserByUsername(authentication.getName()));
            map.put("mutualFriend",mutualFriend);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
