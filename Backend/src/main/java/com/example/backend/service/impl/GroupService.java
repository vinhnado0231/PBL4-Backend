package com.example.backend.service.impl;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;
import com.example.backend.model.GroupUser;
import com.example.backend.model.Message;
import com.example.backend.repository.IGroupRepository;
import com.example.backend.repository.IGroupUserRepository;
import com.example.backend.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IGroupUserRepository groupUserRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public Group findGroupById(Long idGroup) {
        return groupRepository.findById(idGroup).orElse(null);
    }

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    @Override
    public ArrayList<GroupDTO> findGroupByIdUser(long idUser) {
        ArrayList<GroupDTO> groupDTOS = new ArrayList<>();
        List<GroupUser> groupUserList = groupUserRepository.getAllByIdUser(idUser);
        GroupDTO groupDTO;
        Group group;
        Message message;
        for (GroupUser groupUser : groupUserList) {
            group = groupUser.getGroup();
            message = messageService.findLastMessage(group);
            if(message == null){
                groupDTO = new GroupDTO(group.getIdGroup(), group.getNameGroup(), group.isSingle(), groupUser.getRoleGroup(),
                        null, null, false,
                        null,
                        0);
                groupDTOS.add(groupDTO);
                break;
            }
            groupDTO = new GroupDTO(group.getIdGroup(), group.getNameGroup(), group.isSingle(), groupUser.getRoleGroup(),
                    message.getIdMessage(), message.getMessage(), message.isText(),
                    userService.getUserByIdUser(message.getIdSender()).getNameUser(),
                    groupUser.getIdReadMessage());
            groupDTOS.add(groupDTO);
        }
        return groupDTOS;
    }
}
