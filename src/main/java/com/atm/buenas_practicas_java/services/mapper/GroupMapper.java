package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.GroupDTO;
import com.atm.buenas_practicas_java.entities.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GroupMapper extends AbstractServiceMapper<Group, GroupDTO> {

    @Override
    public Group toEntity(GroupDTO dto) throws Exception {
        Group group = new Group();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, group);
        return group;
    }

    @Override
    public GroupDTO toDto(Group group) {
        GroupDTO dto = new GroupDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(group, dto);
        return dto;
    }
}