package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.RoleDTO;
import com.atm.buenas_practicas_java.entities.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper extends AbstractServiceMapper<Role, RoleDTO> {

    @Override
    public RoleDTO toDto(Role entity) {
        if (entity == null) return null;
        return new RoleDTO(entity.getId(), entity.getName());
    }

    @Override
    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }

    @Override
    public List<RoleDTO> toDto(List<Role> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Role> toEntity(List<RoleDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
