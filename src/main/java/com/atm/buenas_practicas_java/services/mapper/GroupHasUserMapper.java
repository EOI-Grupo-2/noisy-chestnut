package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.GroupHasUserDTO;
import com.atm.buenas_practicas_java.entities.Groups_Has_User;
import com.atm.buenas_practicas_java.entities.GroupHasUserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GroupHasUserMapper {

    default GroupHasUserDTO toDTO(Groups_Has_User entity) {
        if (entity == null) return null;
        GroupHasUserDTO dto = new GroupHasUserDTO();
        dto.setGroupId(entity.getGroup().getId());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }

    default Groups_Has_User toEntity(GroupHasUserDTO dto) {
        if (dto == null) return null;
        Groups_Has_User entity = new Groups_Has_User();
        GroupHasUserId id = new GroupHasUserId(dto.getGroupId(), dto.getUserId());
        entity.setId(id)    ;
        // Aquí necesitarás cargar los objetos Group y User según ids (o hacerlo en el servicio)
        return entity;
    }
}
