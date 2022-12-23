package com.example.backend.ultil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class ScheduledTasks {
    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    public static List<String> userOnline = new ArrayList<>();

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
    @Scheduled(fixedRate = 1000 * 30)
    public void getUsersFromSessionRegistry() {
        userOnline = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
    }

}