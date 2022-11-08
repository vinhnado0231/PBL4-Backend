package com.example.backend.service.impl;

import com.example.backend.model.Group;
import com.example.backend.model.User;
import com.example.backend.repository.IGroupRepository;
import com.example.backend.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;
    @Override
    public Group findGroupById(Long idGroup) {
        return groupRepository.findById(idGroup).orElse(null);
    }

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }
}
