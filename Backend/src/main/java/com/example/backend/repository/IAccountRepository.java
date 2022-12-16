package com.example.backend.repository;

import com.example.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
    Account findAccountByUser_EmailUser(String email);
    Account findAccountByToken(String token);

    Account findByIdAccount(long id);

}
