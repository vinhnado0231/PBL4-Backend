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

    @Query(value = "select * from friend join user on user.id_user = friend.id_friend" +
            "where friend.id_user=? and is_request=false and user.name_user like concat('%'?'%')", nativeQuery = true)
    List<Friend> searchFriend(long idUser, String search);

    @Query(value = "SELECT u.user_id, u.name, \n" +
            "       COUNT(f3.friend_id) mutual_friends \n" +
            "FROM friends f1 \n" +
            "INNER JOIN friends f2 ON f2.user_id = f1.friend_id \n" +
            "INNER JOIN user u ON u.user_id = f2.user_id \n" +
            "LEFT JOIN friend f3 ON f3.user_id = f1.user_id AND f3.friend_id = f2.friend_id \n" +
            "WHERE f1.user_id = ? \n" +
            "and f2.user_id =? " +
            "and f1.friend_request = false;", nativeQuery = true)
    Friend isFriend(long idUser1, long idUser2);
}
