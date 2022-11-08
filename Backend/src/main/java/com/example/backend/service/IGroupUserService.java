package com.example.backend.service;

import com.example.backend.model.Group;
import com.example.backend.model.User;

import java.util.List;

public interface IGroupUserService {
    public void addUserToGroup(List<User> users, Group group);
}
