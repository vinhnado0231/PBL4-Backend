package com.example.backend.repository;

import com.example.backend.model.Group;
import com.example.backend.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "select * from message where id_group=?1", nativeQuery = true)
    Slice<Message> getAllByIdGroup(long id_group, Pageable pageable);

    //    @Query(value = "select * from group_user where id_user=?1", nativeQuery = true)
    Message findTopByGroupOrderByIdMessageDesc(Group group);
}
