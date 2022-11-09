package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.model.MessageReact;

public interface IMessageReactService {
    void saveMessageReact(MessageReact messageReact);

//    MessageReact findMessageReactByIdSender(long idSender, Message message);
}
