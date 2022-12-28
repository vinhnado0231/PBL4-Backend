package com.example.backend.service;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;
import com.example.backend.model.User;

import java.util.ArrayList;

public interface IGroupService {
    Group findGroupById(Long idGroup);

    long saveGroup(Group group);

    ArrayList<GroupDTO> findGroupByIdUser(long idUser);

    boolean checkUserInGroup(String username, long idGroup);
}
