package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.service.impl.GroupService;
import com.example.backend.service.impl.MessageService;
import com.example.backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
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

    @GetMapping("/change-status-message")
    public ResponseEntity<Message> changeMessageStatus(@RequestParam Long idGroup, @RequestBody Message message) {
        message.setGroup(groupService.findGroupById(idGroup));
        messageService.saveMessage(message);
        return new ResponseEntity<Message>(HttpStatus.OK);
    }
}
