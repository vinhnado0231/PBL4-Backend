package com.example.backend.controller;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;
import com.example.backend.model.User;
import com.example.backend.service.IAccountService;
import com.example.backend.service.impl.GroupService;
import com.example.backend.service.impl.GroupUserService;
import com.example.backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.backend.ultil.ScheduledTasks.userOnline;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private IAccountService accountService;

    @GetMapping("/get-all-group")
    public ResponseEntity<ArrayList<GroupDTO>> getAllGroupByIdUser(Authentication authentication) {
        long idUser = accountService.getIdUserByUsername(authentication.getName());
        try{
            new Thread(() -> {
                if(!userOnline.containsKey(idUser)){
                    userOnline.put(idUser, LocalDateTime.now().plusMinutes(1));
                    accountService.changeStatusByIdUser( idUser, true);
                }
            }).start();
        }catch (Exception e){

        }
        ArrayList<GroupDTO> groups = groupService.findGroupByIdUser(idUser);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping("/create-group")
    public ResponseEntity<Group> createGroup(@RequestBody List<Long> idUserList, Authentication authentication) {
        Group group = new Group();
        group.setSingle(false);
        StringBuilder nameGroup = new StringBuilder();
        List<User> users = new ArrayList<>();
        users.add(userService.getUserByIdUser(accountService.getIdUserByUsername(authentication.getName())));
        User user;
        for (Long id : idUserList) {
            user = userService.getUserByIdUser(id);
            if (user != null) {
                nameGroup.append(user.getNameUser()).append(", ");
                users.add(user);
            }
        }
        nameGroup = new StringBuilder(nameGroup.substring(0, nameGroup.length() - 2).replaceAll("\\s{2,}", " ").trim());
        group.setNameGroup(nameGroup.toString());
        groupService.saveGroup(group);
        groupUserService.addUserToGroup(users, group, users.get(0).getIdUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-user-to-group")
    public ResponseEntity<Object> addUserToGroup(@RequestBody List<Long> idUserList, @RequestParam Long idGroup) {
        Group group = groupService.findGroupById(idGroup);
        List<User> users = new ArrayList<>();
        for (Long id : idUserList) {
            users.add(userService.getUserByIdUser(id));
        }
        groupUserService.addUserToGroup(users, group, null);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update-group")
    public ResponseEntity<Object> updateGroup(@RequestBody Map<String, String> json, @RequestParam Long idGroup, Authentication authentication) {
        Group group = groupService.findGroupById(idGroup);
        if (groupUserService.getGroupUserByIdUserIdGroup(accountService.getIdUserByUsername(authentication.getName()), idGroup).getRoleGroup() == 1) {
            if (json.get("nameGroup") != null) {
                group.setNameGroup(json.get("nameGroup"));
            }
            if (json.get("avt") != null) {
                group.setAvatarGroup(json.get("avt"));
            }
            groupService.saveGroup(group);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
