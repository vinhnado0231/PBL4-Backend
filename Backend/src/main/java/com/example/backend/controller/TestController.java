package com.example.backend.controller;

import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IAccountRepository;
import com.example.backend.repository.IUserFavoriteRepository;
import com.example.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Controller
public class TestController {
    @Autowired
    private  IAccountRepository accountRepository;
    @Autowired
    private  IUserRepository userRepository;
    @Autowired
    private  IUserFavoriteRepository userFavoriteRepository;

    @GetMapping("/test")
    public ResponseEntity<Object> checkUsername()  {
        String url = "E:\\data.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(url);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(fileInputStream);
        try {
            int count =0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words=line.split("/");

                User user = new User();
                user.setNameUser(words[0]);
                user.setUserFavorite(userFavoriteRepository.findByIdUserFavorite(count++));
                user.setAccount(accountRepository.findByIdAccount(count+7));
                user.setAddressUser(words[2]);
                user.setDateOfBirthUser(words[1]);
                user.setHomeTownUser(words[3]);
                userRepository.save(user);
            }
        } finally {
            try {
                scanner.close();
                fileInputStream.close();
            } catch (IOException ex) {

            }
        }
//
//
//

//        User user = new User();
//        user.setNameUser("Phuc");
//        userRepository.save(user);
        //        UserFavorite userFavorite = new UserFavorite(1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f);

        //        UserFavorite userFavorite = new UserFavorite(1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f);

//        User user = new User();
//        user.setAccount(account);
//        user.setUserFavorite(userFavorite);
//        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
