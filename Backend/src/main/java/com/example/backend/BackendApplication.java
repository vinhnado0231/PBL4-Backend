package com.example.backend;

import com.example.backend.controller.TestController;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IAccountRepository;
import com.example.backend.repository.IUserFavoriteRepository;
import com.example.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        doSomeThing();
    }
    @Autowired
    private static IAccountRepository accountRepository;
    @Autowired
    private static IUserRepository userRepository;
    @Autowired
    private static IUserFavoriteRepository userFavoriteRepository;

    public static void doSomeThing(){
        //Doc fle

        Account account = new Account("username", "password");

        UserFavorite userFavorite = new UserFavorite(1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f);

        User user = new User();
        user.setAccount(account);
        user.setUserFavorite(userFavorite);
        userRepository.save(user);
    }

}
