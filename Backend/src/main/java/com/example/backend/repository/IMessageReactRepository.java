package com.example.backend.repository;

import com.example.backend.model.Message;
import com.example.backend.model.MessageReact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageReactRepository extends JpaRepository<MessageReact,Long>{
//    MessageReact findMessageReactById_senderAndMessage(long idSender, Message message);
}
