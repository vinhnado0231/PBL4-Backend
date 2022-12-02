package com.example.backend.controller;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;
import com.example.backend.model.User;
import com.example.backend.service.impl.GroupService;
import com.example.backend.service.impl.GroupUserService;
import com.example.backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupUserService groupUserService;

    @GetMapping("/get-all-group")
    public ResponseEntity<ArrayList<GroupDTO>> getAllGroupByIdUser(@RequestParam Long idUser) {
        ArrayList<GroupDTO> groups = groupService.findGroupByIdUser(idUser);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping("/create-group")
    public ResponseEntity<Group> createGroup(@RequestBody List<Long> idUserList) {
        Group group = new Group();
        groupService.saveGroup(group);
        List<User> users = new ArrayList<>();
        for (Long id : idUserList) {
            users.add(userService.getUserByIdUser(id));
        }
        groupUserService.addUserToGroup(users, group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-user-to-group")
    public ResponseEntity<Group> sendMessagse(@RequestBody List<Long> idUserList, @RequestParam Long idGroup) {
        Group group = groupService.findGroupById(idGroup);
        List<User> users = new ArrayList<>();
        for (Long id : idUserList) {
            users.add(userService.getUserByIdUser(id));
        }
        groupUserService.addUserToGroup(users, group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/get-all-group")
//    public ResponseEntity<Object> getAllGroup(@RequestParam long idUser){
//
//    }
}