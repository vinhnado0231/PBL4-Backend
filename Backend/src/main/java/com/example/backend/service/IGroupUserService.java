package com.example.backend.service;

import com.example.backend.model.Group;
import com.example.backend.model.GroupUser;
import com.example.backend.model.User;

import java.util.List;

public interface IGroupUserService {
    GroupUser findGroupUserByIdGroupUser(long id);

    void addUserToGroup(List<User> users, Group group);

    void changeidReadMessage(long id, GroupUser groupUser);

    GroupUser getGroupUserByIdUserIdGroup(long idUser, long idGroup);

    List<GroupUser> getAllByIdUser(long idUser);

}
