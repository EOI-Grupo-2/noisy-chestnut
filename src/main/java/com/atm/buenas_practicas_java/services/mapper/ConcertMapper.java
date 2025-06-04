package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Place;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConcertMapper {

    public ConcertDTO toDto(Concert entity) {
        if (entity == null) return null;

        ConcertDTO dto = new ConcertDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate());

        // Extraemos el ID del lugar si existe
        if (entity.getPlace() != null) {
            dto.setPlaceId(entity.getPlace().getId());
        }

        return dto;
    }

    public Concert toEntity(ConcertDTO dto) {
        if (dto == null) return null;

        Concert entity = new Concert();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());

        // Creamos solo el objeto Place con el ID (suficiente para JPA)
        if (dto.getPlaceId() != null) {
            Place place = new Place();
            place.setId(dto.getPlaceId());
            entity.setPlace(place);
        }

        return entity;
    }

    public List<ConcertDTO> toDto(List<Concert> entityList) {
        return entityList == null ? List.of() : entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
