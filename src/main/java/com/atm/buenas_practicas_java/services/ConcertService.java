package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConcertService extends AbstractBusinessService<Concert, Long, ConcertDTO, ConcertRepository, ConcertMapper>{

    public ConcertService(ConcertRepository concertRepository, ConcertMapper mapper) {
        super(concertRepository, mapper);
    }

    public List<ConcertDTO> searchConcertsByName(String name) {
        List<Concert> concerts = getRepo().findByNameContainingIgnoreCase(name);
        return concerts.stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }


}