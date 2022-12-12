package com.example.backend.repository;


import com.example.backend.model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserFavoriteRepository extends JpaRepository<UserFavorite, Long>{
}
