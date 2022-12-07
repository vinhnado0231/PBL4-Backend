package com.example.backend.repository;

import com.example.backend.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFriendRepository extends JpaRepository<Friend,Long>{

    List<Friend> findAllByIdFriend(long idUser);

    @Query(value = "select * from friend where id_user=?",nativeQuery = true)
    List<Friend> findAllByIdUser(long idUser);
}
