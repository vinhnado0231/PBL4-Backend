package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/testHehe")
    public ResponseEntity<Object> sendFriendRequest() {
        Account account = new Account();
        for (int i = 4; i < 100; i++) {
            User user = userService.getUserByIdUser((long)i);
            account.setUsername("user"+i);
            account.setPassword("$2a$10$FZpY0QLTrvsIE7wMkEGtzeEwsT0Ik8pJbtO6JM4VlxAvut2xyxAgK");
            account.setStatus(false);
            user.setAccount(account);
            userRepository.save(user);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
