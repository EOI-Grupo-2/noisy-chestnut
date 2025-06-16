package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.repositories.PlaceRepository;
import com.atm.buenas_practicas_java.services.mapper.PlaceMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceService extends AbstractBusinessService<Place, Long, PlaceDTO, PlaceRepository, PlaceMapper> {

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper) {
        super(placeRepository, placeMapper);
    }

    public Optional<Place> findByName(String name) {
        return this.getRepo().findByName(name);
    }

    public Optional<PlaceDTO> findDTOByName(String name) {
        return this.getRepo().findByName(name)
                .map(place -> this.getMapper().toDto(place));
    }
    public Optional<Place> findById(Long id) {
        PlaceService placeRepository = null;
        return placeRepository.findById(id);
    }

}
