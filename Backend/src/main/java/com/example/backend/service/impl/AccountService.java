package com.example.backend.service.impl;

import com.example.backend.controller.AccountController;
import com.example.backend.model.Account;
import com.example.backend.repository.IAccountRepository;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.IAccountService;
import com.example.backend.ultil.ForgotPasswordToken;
import com.example.backend.ultil.ScheduledTasks;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountService implements IAccountService, UserDetailsService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);

        if (account == null) {
            throw new UsernameNotFoundException("account not found");
        }
        //Chuan bi cho chuc nang chia role
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        return new org.springframework.security.core.userdetails.User(
                account.getUsername(), account.getPassword(), grantedAuthorities
        );
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void saveForgotPassword(Account account, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        account.setPassword(encodedPassword);
        account.setToken(null);
        accountRepository.save(account);
    }

    @Override
    public void updateToken(Account account) {
        try {
            String token = RandomString.make(45);
            account.setToken(token);
            ScheduledTasks.tokenForgotPasswordSet.add(new ForgotPasswordToken(account.getToken(), LocalDateTime.now().plusMinutes(10)));
        } catch (Exception e) {
            updateToken(account);
            return;
        }
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByToken(String token) {
        return accountRepository.findAccountByToken(token);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByUser_EmailUser(email);
    }

    @Override
    public void saveAccount(Account account) {
         accountRepository.save(account);
    }

    @Override
    public long getIdUserByUsername(String username) {
        return userRepository.findUserByAccount_Username(username).getIdUser();
    }

    @Override
    public void changeTimeOff(Account account) {
        account.setTimeOff(LocalDateTime.now().toString());
        saveAccount(account);
    }


}
