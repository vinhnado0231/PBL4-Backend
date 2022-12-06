package com.example.backend.controller;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.model.Account;
import com.example.backend.service.impl.AccountService;
import com.example.backend.service.impl.UserService;
import com.example.backend.ultil.EncrypPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-valid-email")
    public ResponseEntity<Object> checkEmail(@RequestParam String email) {
        if (accountService.findAccountByEmail(email) != null) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/acc")
    public ResponseEntity<Object> createAccount() {
        Account account = new Account();
        account.setUsername("user");
        account.setPassword(EncrypPasswordUtils.EncrypPasswordUtils("123456"));
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create-new-user")
    public ResponseEntity<Object> createUser(@RequestBody UserCreateDTO user) {
        if (accountService.getAccountByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        if (accountService.findAccountByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
