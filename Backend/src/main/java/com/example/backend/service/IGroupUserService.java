package com.example.backend.service;

import com.example.backend.model.Group;
import com.example.backend.model.GroupUser;
import com.example.backend.model.User;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IGroupUserService {
    GroupUser findGroupUserByIdGroupUser(long id);

    void addUserToGroup(List<User> users, Group group,@Nullable Long idLeader);

    void changeidReadMessage(long idReadMessage,long idGroup,long idUser);

    GroupUser getGroupUserByIdUserIdGroup(long idUser, long idGroup);

    List<GroupUser> getAllByIdUser(long idUser);
}
