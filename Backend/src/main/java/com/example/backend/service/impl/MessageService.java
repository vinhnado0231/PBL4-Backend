package com.example.backend.service.impl;

import com.example.backend.model.Group;
import com.example.backend.model.Message;
import com.example.backend.repository.IMessageRepository;
import com.example.backend.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;

    @Override
    public Slice<Message> getAllMessageByIdGroup(long idGroup, Pageable pageable) {
        return messageRepository.getAllByIdGroup(idGroup, pageable);
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Message getMessageByIdMessage(Long idMessage) {
        return messageRepository.findById(idMessage).orElse(null);
    }

    @Override
    public void changeStatusMessage(Long idMessage) {

    }

    @Override
    public Message findLastMessage(Group group) {
        return messageRepository.findTopByGroupOrderByIdMessageDesc(group);
    }
}
