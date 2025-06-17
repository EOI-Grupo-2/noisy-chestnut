package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.repositories.PlaceRepository;
import com.atm.buenas_practicas_java.services.mapper.PlaceMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService extends AbstractBusinessService<Place, Long, PlaceDTO, PlaceRepository, PlaceMapper> {

    private final ConcertRepository concertRepository;

    public PlaceService(PlaceRepository placeRepository, PlaceMapper placeMapper, ConcertRepository concertRepository, PlaceRepository placeRepository1) {
        super(placeRepository, placeMapper);
        this.concertRepository = concertRepository;
    }

    public Optional<Place> findByName(String name) {
        return this.getRepo().findByName(name);
    }

    public Optional<PlaceDTO> findDTOByName(String name) {
        return this.getRepo().findByName(name)
                .map(place -> this.getMapper().toDto(place));
    }

    public List<PlaceDTO> searchPlacesByName(String name) {
        List<Place> places = getRepo().findByNameContainingIgnoreCase(name);
        return places.stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }
}
