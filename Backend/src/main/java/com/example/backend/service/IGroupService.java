package com.example.backend.service;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;

import java.util.ArrayList;

public interface IGroupService {
    Group findGroupById(Long idGroup);

    void saveGroup(Group group);

    ArrayList<GroupDTO> findGroupByIdUser(long idUser);
}
