package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.RoleDTO;
import com.atm.buenas_practicas_java.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractServiceMapper<Role, RoleDTO> {
    @Override
    public RoleDTO toDto(Role entity) {
        RoleDTO roleDTO = new RoleDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(entity, roleDTO);
        return roleDTO;
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        Role role = new Role();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(dto, role);
        return role;
    }
}
