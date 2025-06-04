package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.RoleDTO;
import com.atm.buenas_practicas_java.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toDto(Role role) {
        if (role == null) return null;
        return new RoleDTO(role.getId(), role.getName());
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }

    public static List<RoleDTO> toDtoList(List<Role> roles) {
        return roles.stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Role> toEntityList(List<RoleDTO> dtos) {
        return dtos.stream()
                .map(RoleMapper::toEntity)
                .collect(Collectors.toList());
    }
}
