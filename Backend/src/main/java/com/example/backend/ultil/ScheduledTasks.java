package com.example.backend.ultil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class ScheduledTasks {
    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    public static Set<ForgotPasswordToken> tokenForgotPasswordSet = new LinkedHashSet<>();
    public static List<UserDetails> userOnline = new LinkedList<>();

    @Async
    @Scheduled(fixedRate = 1000 * 10)
    public void checkForgotPasswordTokenExprired() {
        for (ForgotPasswordToken tokenCheck : tokenForgotPasswordSet) {
            int diff = LocalDateTime.now().compareTo(tokenCheck.getTime());
            if (diff >= 0) {
                tokenForgotPasswordSet.remove(tokenCheck);
            }
        }
    }

    @Async
    @Scheduled(fixedRate = 1000 * 30)
    public void findAllLoggedInUsers() {
        userOnline = sessionRegistry.getAllPrincipals()
                .stream()
                .filter(principal -> principal instanceof UserDetails)
                .map(UserDetails.class::cast)
                .collect(Collectors.toList());
    }


}