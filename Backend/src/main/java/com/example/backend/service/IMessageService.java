package com.example.backend.service;

import com.example.backend.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface IMessageService {
    public Slice<Message> getAllMessageByIdGroup(long idGroup, Pageable pageable);
    public void saveMessage(Message message);
    public Message getMessageByIdMessage(Long idMessage);
    public void changeStatusMessage(Long idMessage);
}
