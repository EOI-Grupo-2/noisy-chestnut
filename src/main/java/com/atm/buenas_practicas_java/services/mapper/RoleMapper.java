package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.RoleDTO;
import com.atm.buenas_practicas_java.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractServiceMapper<Role, RoleDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDTO toDto(Role entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, RoleDTO.class);
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, Role.class);
    }
}
