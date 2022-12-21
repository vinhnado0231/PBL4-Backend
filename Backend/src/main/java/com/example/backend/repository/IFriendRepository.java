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

    @Query(value = "SELECT DISTINCT \n" +
            "FROM \n" +
            "    Friend f1\n" +
            "    INNER JOIN Friends f2 \n" +
            "    ON f1.user1 = f2.user1 \n" +
            "    OR f1.user1 = f2.user2 \n" +
            "    OR f1.user2 = f2.user1 \n" +
            "    OR f1.user2 = f2.user2 \n" +
            "    INNER JOIN Orders o \n" +
            "    ON (f1.user1 = o.by_user \n" +
            "    OR f1.user2 = o.by_user \n" +
            "    OR f2.user1 = o.by_user \n" +
            "    OR f2.user2 = o.by_user) \n" +
            "WHERE   \n" +
            "    (f1.user1 = 1\n" +
            "    OR f1.user2 = 1)", nativeQuery = true)
    List<Friend> checkSimilarFriend(long idUser1, long idUser2);
}
