package com.example.backend.ultil;

import com.example.backend.model.Account;
import com.example.backend.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class ScheduledTasks {
    @Autowired
    private IAccountService accountService;

    public static Map<Long, LocalDateTime> userOnline = new HashMap<>();

    public static Set<ForgotPasswordToken> tokenForgotPasswordSet = new LinkedHashSet<>();

    @Async
    @Scheduled(fixedRate = 1000 * 10)
    public void CheckForgotPasswordTokenExprired() {
        for (ForgotPasswordToken tokenCheck : tokenForgotPasswordSet) {
            int diff = LocalDateTime.now().compareTo(tokenCheck.getTime());
            if (diff >= 0) {
                tokenForgotPasswordSet.remove(tokenCheck);
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 10)
    public void getUsersOnline() {
        userOnline.forEach((k, v) -> {
            int diff = LocalDateTime.now().compareTo(v);
            if (diff >= 0) {
                Account account = accountService.getAccountByIdUser(k);
                account.setStatus(false);
                accountService.saveAccount(account);
                userOnline.remove(k,v);
            }
        });
    }

}