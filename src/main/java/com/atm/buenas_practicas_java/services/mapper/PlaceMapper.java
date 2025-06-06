package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class PlaceMapper extends AbstractServiceMapper<Place, PlaceDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public PlaceDTO toDto(Place entity) {
        if (entity == null) return null;

        ModelMapper mapper = new ModelMapper();
        PlaceDTO dto = mapper.map(entity, PlaceDTO.class);

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        if (entity.getRating() != null) {
            dto.setRating(entity.getRating().toPattern());
        }

        return dto;
    }

    @Override
    public Place toEntity(PlaceDTO dto) {
        if (dto == null) return null;

        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(dto, Place.class);

        if (dto.getRating() != null) {
            place.setRating(new DecimalFormat(dto.getRating()));
        }
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            place.setUser(user);
        }

        return place;
    }
}
