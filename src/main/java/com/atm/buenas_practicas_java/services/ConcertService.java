package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import org.springframework.stereotype.Service;


@Service
public class ConcertService extends AbstractBusinessService<Concert, Long, ConcertDTO, ConcertRepository, ConcertMapper>{

    public ConcertService(ConcertRepository concertRepository, ConcertMapper mapper) {
        super(concertRepository, mapper);
    }
}