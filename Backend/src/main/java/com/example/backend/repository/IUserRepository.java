package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findUserByAccount_Username(String username);

}
