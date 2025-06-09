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

    @Override
    public PlaceDTO toDto(Place entity) {
        PlaceDTO dto = new PlaceDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(entity, dto);
        return dto;
    }

    @Override
    public Place toEntity(PlaceDTO dto) {
        Place place = new Place();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, Place.class);
        return place;
    }

}
