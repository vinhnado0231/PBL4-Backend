package com.example.backend.repository;

import com.example.backend.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByIdFriend(long idUser);

    @Query(value = "select * from friend where id_user=? and is_request=false", nativeQuery = true)
    List<Friend> findAllFriendByIdUser(long idUser);

    @Query(value = "select * from friend where id_user=? and is_request=true", nativeQuery = true)
    List<Friend> findAllFriendRequestByIdUser(long idUser);

    @Query(value = "select * from friend where id_user=? and id_friend=?", nativeQuery = true)
    Friend findFriendByIdUserAndIdFriend(long idUser, long idFriend);

    @Query(value = "select * from friend join user on user.", nativeQuery = true)
    Friend searchAllByNameFriend(long idUser, String search);
}
