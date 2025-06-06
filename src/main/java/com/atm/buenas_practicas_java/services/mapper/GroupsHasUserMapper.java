package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.GroupsHasUserDTO;
import com.atm.buenas_practicas_java.entities.Groups_Has_User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupsHasUserMapper extends AbstractServiceMapper<Groups_Has_User, GroupsHasUserDTO> {

    @Override
    public GroupsHasUserDTO toDto(Groups_Has_User entity) {
        if (entity == null) return null;

        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, GroupsHasUserDTO.class);
    }

    @Override
    public Groups_Has_User toEntity(GroupsHasUserDTO dto) {
        if (dto == null) return null;

        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Groups_Has_User.class);
    }
}
