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
    public void changeidReadMessage(long id, long idGroup,long idUser) {
        GroupUser groupUser = getGroupUserByIdUserIdGroup(idUser,idGroup);
        groupUser.setIdReadMessage(id);
        groupUserRepository.save(groupUser);
    }

    @Override
    public GroupUser getGroupUserByIdUserIdGroup(long idUser, long idGroup) {
        return groupUserRepository.getGroupUserByIdUserIdGroup(idUser,idGroup);
    }

    @Override
    public List<GroupUser> getAllByIdUser(long idUser) {
        return groupUserRepository.getAllByIdUser(idUser);
    }
}
