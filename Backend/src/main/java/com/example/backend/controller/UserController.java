package com.example.backend.controller;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.model.User;
import com.example.backend.service.IAccountService;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserFavoriteService;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserFavoriteService userFavoriteService;

    @GetMapping("/check-valid-username")
    public ResponseEntity<Object> CheckUsername(@RequestParam String username) {
        if (accountService.getAccountByUsername(username) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-valid-email")
    public ResponseEntity<Object> CheckEmail(@RequestParam String email) {
        if (accountService.findAccountByEmail(email) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-info-user")
    public ResponseEntity<Object> CheckEmail(@RequestParam long idUser, Authentication authentication) {
        try {
            Map<String, Object> map = new HashMap<>();
            User user = userService.getUserByIdUser(idUser);
            user.setAccount(null);
            map.put("user", user);
            long mutualFriend = friendService.getMutualFriend(idUser, accountService.getIdUserByUsername(authentication.getName()));
            map.put("mutualFriend", mutualFriend);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-new-user")
    public ResponseEntity<Object> CreateUser(@RequestBody UserCreateDTO user) {
        if (accountService.getAccountByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (accountService.findAccountByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-user-favorite")
    public ResponseEntity<Object> AddUserFavorite(@RequestBody Map<String, Float> json, Authentication authentication) {
        userFavoriteService.UpdateUserFavorite(accountService.getIdUserByUsername(authentication.getName()), json);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-user-favorite")
    public ResponseEntity<Object> GetUserFavorite(Authentication authentication) {
        return new ResponseEntity<>(userFavoriteService.findUserFavoriteByIdUser(accountService.getIdUserByUsername(authentication.getName())), HttpStatus.OK);
    }

    @GetMapping("/search-user-by-email")
    public ResponseEntity<Object> FindUserByEmail(@RequestParam String search, Authentication authentication) {
        try {
            Map<String, Object> map = new HashMap<>();
            User user = userService.FindByEmail(search);
            user.setAccount(null);
            map.put("user", user);
            long mutualFriend = friendService.getMutualFriend(user.getIdUser(), accountService.getIdUserByUsername(authentication.getName()));
            map.put("mutualFriend", mutualFriend);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
