package com.example.backend.ultil;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.backend.controller.AccountController.tokenForgotPasswordSet;
import static com.example.backend.controller.SecurityController.loginSessionSet;


@Component
@EnableAsync
public class ScheduledTasks {
    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void checkForgotPasswordTokenExprired() {
        for (ForgotPasswordToken tokenCheck : tokenForgotPasswordSet) {
            int diff = LocalDateTime.now().compareTo(tokenCheck.getTime());
            if (diff >= 0) {
                tokenForgotPasswordSet.remove(tokenCheck);
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 60)
    public void checkLoginSessionExpired() {
        for (LoginSession tokenCheck : loginSessionSet) {
            int diff = LocalDateTime.now().compareTo(tokenCheck.getTime());
            if (diff >= 0) {
                tokenForgotPasswordSet.remove(tokenCheck);
            }
        }
    }
}