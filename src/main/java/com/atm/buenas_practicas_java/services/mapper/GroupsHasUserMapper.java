package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.GroupsHasUserDTO;
import com.atm.buenas_practicas_java.entities.GroupsHasUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupsHasUserMapper extends AbstractServiceMapper<GroupsHasUser, GroupsHasUserDTO> {

    @Override
    public GroupsHasUserDTO toDto(GroupsHasUser entity) {
        if (entity == null) return null;

        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, GroupsHasUserDTO.class);
    }

    @Override
    public GroupsHasUser toEntity(GroupsHasUserDTO dto) {
        if (dto == null) return null;

        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, GroupsHasUser.class);
    }
}
