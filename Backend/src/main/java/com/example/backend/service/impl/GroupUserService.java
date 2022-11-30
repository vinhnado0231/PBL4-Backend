package com.example.backend.service.impl;

import com.example.backend.model.Group;
import com.example.backend.model.GroupUser;
import com.example.backend.model.User;
import com.example.backend.repository.IGroupUserRepository;
import com.example.backend.service.IGroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserService implements IGroupUserService {
    @Autowired
    private IGroupUserRepository groupUserRepository;

    @Override
    public GroupUser findGroupUserByIdGroupUser(long id) {
        return groupUserRepository.findById(id).orElse(null);
    }

    @Override
    public void addUserToGroup(List<User> users, Group group) {
        for (User user : users) {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroup(group);
            groupUser.setUser(user);
            groupUserRepository.save(groupUser);
        }
    }

    @Override
    public void changeidReadMessage(long id, GroupUser groupUser) {
        groupUser.setIdReadMessage(id);
        groupUserRepository.save(groupUser);
    }

    @Override
    public List<GroupUser> getAllGroupUserByIdUser(long idUser) {
        return null;
    }
}
