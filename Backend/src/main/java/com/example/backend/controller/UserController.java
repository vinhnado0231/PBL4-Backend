package com.example.backend.controller;

import com.example.backend.dto.UserCreateDTO;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.impl.AccountService;
import com.example.backend.service.impl.UserService;
import com.example.backend.ultil.EncrypPasswordUtils;
import com.example.backend.ultil.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.ScheduledTask;
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
        return  new ResponseEntity<>(ScheduledTasks.userOnline,HttpStatus.OK);
    }

//    @Autowired
//    private IUserRepository userRepository;
//    @GetMapping("/check-valid-acc")
//    public ResponseEntity<Object> checkonline1() {
//        Account account = new Account("user1", EncrypPasswordUtils.EncrypPasswordUtils("123456"));
//        User user = userRepository.findById(1L).orElse(null);
//        user.setAccount(account);
//        userRepository.save(user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

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
