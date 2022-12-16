package com.example.backend.service.impl;

import com.example.backend.dto.GroupDTO;
import com.example.backend.model.Group;
import com.example.backend.model.GroupUser;
import com.example.backend.model.Message;
import com.example.backend.repository.IGroupRepository;
import com.example.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;
    @Autowired
    private IGroupUserService groupUserService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;

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
        List<GroupUser> groupUserList = groupUserService.getAllByIdUser(idUser);
        GroupDTO groupDTO;
        Group group;
        Message message;
        for (GroupUser groupUser : groupUserList) {
            group = groupUser.getGroup();
            message = messageService.findLastMessage(group);
            if (message == null) {
                groupDTO = new GroupDTO(group.getIdGroup(), group.getNameGroup(), group.isSingle(), groupUser.getRoleGroup(),
                        null, null, null, null, null, null,
                        null);
                groupDTOS.add(groupDTO);
                continue;
            }
            if(message.getType()==0){
                groupDTO = new GroupDTO(group.getIdGroup(), group.getNameGroup(), group.isSingle(), groupUser.getRoleGroup(),
                        message.getIdMessage(), message.getMessage(), message.getTime(), message.getType(), message.getIdSender(),
                        userService.getUserByIdUser(message.getIdSender()).getNameUser(),
                        groupUser.getIdReadMessage());
                groupDTOS.add(groupDTO);
                continue;
            }
            groupDTO = new GroupDTO(group.getIdGroup(), group.getNameGroup(), group.isSingle(), groupUser.getRoleGroup(),
                    message.getIdMessage(), null, message.getTime(), message.getType(), message.getIdSender(),
                    userService.getUserByIdUser(message.getIdSender()).getNameUser(),
                    groupUser.getIdReadMessage());
            groupDTOS.add(groupDTO);
        }
        return groupDTOS;
    }

    @Override
    public boolean chekUserInGroup(String username, long idGroup) {
        return groupUserService.getGroupUserByIdUserIdGroup(accountService.getIdUserByUsername(username), idGroup) != null ? true : false;
    }
}
