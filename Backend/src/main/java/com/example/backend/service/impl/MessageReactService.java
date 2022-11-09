package com.example.backend.service.impl;

import com.example.backend.model.Message;
import com.example.backend.model.MessageReact;
import com.example.backend.repository.IMessageReactRepository;
import com.example.backend.service.IMessageReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReactService implements IMessageReactService {
    @Autowired
    private IMessageReactRepository messageReactRepository;
    @Override
    public void saveMessageReact(MessageReact messageReact) {
        messageReactRepository.save(messageReact);
    }
//    @Override
//    public MessageReact findMessageReactByIdSender(long idSender, Message message) {
//        return messageReactRepository.findMessageReactById_senderAndMessage(idSender, message);
//    }
}
