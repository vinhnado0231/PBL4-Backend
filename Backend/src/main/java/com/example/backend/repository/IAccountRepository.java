package com.example.backend.repository;

import com.example.backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Long> {
    Account findByUsername(String username);
    Account findAccountByUser_EmailUser(String email);
    Account findAccountByToken(String token);

    Account findByIdAccount(long id);
    @Query(value = "select * from account join user on user.id_account = account.id_account where user.id_user = ?",nativeQuery = true)
    Account findAccountByIdUser(long idUser);

    @Query(value = "select account.status from account join user on user.id_account = account.id_account where user.id_user = ?",nativeQuery = true)
    Boolean checkStatusByIdUser(long idUser);
}
