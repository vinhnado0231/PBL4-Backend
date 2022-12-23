package com.example.backend.repository;


import com.example.backend.model.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGroupUserRepository extends JpaRepository<GroupUser, Long> {
    @Query(value = "select * from group_user where id_user = ?1", nativeQuery = true)
    List<GroupUser> getAllByIdUser(long idUser);

    @Query(value = "select * from group_user where id_user = ?1 and id_group=?2", nativeQuery = true)
    GroupUser getGroupUserByIdUserIdGroup(long idUser, long idGroup);
}
