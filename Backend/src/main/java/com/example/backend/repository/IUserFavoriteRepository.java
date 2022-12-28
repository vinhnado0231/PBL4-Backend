package com.example.backend.repository;


import com.example.backend.model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    UserFavorite findByIdUserFavorite(long idUserFavorite);

    @Query(value = "select * from user_favorite join user on user.id_user_favorite = user_favorite.id_user_favorite where user.id_user =?", nativeQuery = true)
    UserFavorite findByIdUser(long idUser);
}
