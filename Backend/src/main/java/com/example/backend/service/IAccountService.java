package com.example.backend.service;

import com.example.backend.model.Account;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAccountService {
    UserDetails loadUserByUsername(String username);

    Account getAccountByUsername(String username);

    Account getAccountByIdUser(long idUser);

    void saveForgotPassword(Account account, String password);

    void updateToken(Account account);

    Account findAccountByToken(String token);

    Account findAccountByEmail(String email);

    void saveAccount(Account account);

    long getIdUserByUsername(String username);
    Boolean checkStatusByIdUser(long idUser);
    void changeStatusByIdUser(long idUser,boolean status);

}
