package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.GroupDTO;
import com.atm.buenas_practicas_java.entities.Group;
import com.atm.buenas_practicas_java.repositories.GroupRepository;
import com.atm.buenas_practicas_java.services.mapper.GroupMapper;
import org.springframework.stereotype.Service;

@Service
public class GroupService extends AbstractBusinessService<Group, Long, GroupDTO, GroupRepository, GroupMapper> {
    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        super(groupRepository, groupMapper);
    }
}
