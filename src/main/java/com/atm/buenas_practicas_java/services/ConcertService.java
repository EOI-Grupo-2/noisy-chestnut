package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import com.atm.buenas_practicas_java.specifications.ConcertSpecifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConcertService extends AbstractBusinessService<Concert, Long, ConcertDTO, ConcertRepository, ConcertMapper>{


    protected ConcertService(ConcertRepository concertRepository, ConcertMapper mapper) {
        super(concertRepository, mapper);
    }


    public List<ConcertDTO> searchConcerts(String name, String description) {
        return getRepo().findAll(ConcertSpecifications.filterBy(name, description))
                .stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }
}