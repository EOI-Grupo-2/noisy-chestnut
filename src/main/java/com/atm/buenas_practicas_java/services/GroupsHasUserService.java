package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.GroupsHasUserDTO;
import com.atm.buenas_practicas_java.entities.GroupsHasUser;
import com.atm.buenas_practicas_java.repositories.GroupHasUserRepository;
import com.atm.buenas_practicas_java.services.mapper.GroupsHasUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsHasUserService extends AbstractBusinessService<
        GroupsHasUser,
        Long,
        GroupsHasUserDTO,
        GroupHasUserRepository,
        GroupsHasUserMapper> {

    public GroupsHasUserService(GroupHasUserRepository repo, GroupsHasUserMapper mapper) {
        super(repo, mapper);
    }
}
