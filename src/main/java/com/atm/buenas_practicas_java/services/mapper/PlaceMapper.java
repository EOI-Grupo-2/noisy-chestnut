package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceMapper extends AbstractServiceMapper<Place, PlaceDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public PlaceDTO toDto(Place entity) {
        if (entity == null) return null;

        return new PlaceDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getCapacity(),
                entity.getRating().toPattern(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    @Override
    public Place toEntity(PlaceDTO dto) {
        if (dto == null) return null;

        Place place = new Place();
        place.setId(dto.getId());
        place.setName(dto.getName());
        place.setDescription(dto.getDescription());
        place.setAddress(dto.getAddress());
        place.setCapacity(dto.getCapacity());
        place.setRating(new DecimalFormat(dto.getRating()));
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            place.setUser(user);
        }
        return place;
    }

    @Override
    public List<PlaceDTO> toDto(List<Place> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Place> toEntity(List<PlaceDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
