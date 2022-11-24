package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.model.MessageReact;
import com.example.backend.service.impl.*;
import com.example.backend.ultil.LoginSession;
import com.example.backend.ultil.MessageSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private MessageReactService messageReactService;
    @Autowired
    private MessageService messageService;

    public static Set<MessageSession> messageSessionSet = new LinkedHashSet<>();

    @GetMapping("/get-message")
    public ResponseEntity<List<Message>> getMessage(@RequestParam Long idGroup
            , @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Slice<Message> messageSlice = messageService.getAllMessageByIdGroup(idGroup, pageable);
        List<Message> messages = messageSlice.getContent();
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestParam Long idGroup, @RequestBody Message message) {
        message.setGroup(groupService.findGroupById(idGroup));
        messageService.saveMessage(message);
        return new ResponseEntity<Message>(HttpStatus.OK);
    }

    @GetMapping("/change-read-message")
    public ResponseEntity<Message> changeReadMessage(@RequestParam long idGroupUser, @RequestParam long idReadMessage) {
        groupUserService.changeidReadMessage(idReadMessage, groupUserService.findGroupUserById(idGroupUser));
        return new ResponseEntity<Message>(HttpStatus.OK);
    }

//    @GetMapping("/send-react-message")
//    public ResponseEntity<Message> sendReactMessage(@RequestParam long idMessage, @RequestParam long react, @RequestParam long idSender) {
//        MessageReact messageReact = messageReactService.findMessageReactByIdSender(idSender, messageService.getMessageByIdMessage(idMessage));
//        if (messageReact != null) {
//            messageReact.setReact(react);
//        } else {
//            messageReact = new MessageReact();
//            messageReactService.saveMessageReact(messageReact);
//        }
//        return new ResponseEntity<Message>(HttpStatus.OK);
//    }

}
