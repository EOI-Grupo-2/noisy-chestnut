package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ConcertMapper extends AbstractServiceMapper<Concert, ConcertDTO> {

    @Override
    public ConcertDTO toDto(Concert concert) {
        ConcertDTO concertDTO = new ConcertDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(concert,concertDTO);
        return concertDTO;
    }

    @Override
    public Concert toEntity(ConcertDTO concertDTO) throws Exception {
        Concert concert = new Concert();
        ModelMapper mapper = new ModelMapper();
        mapper.map(concertDTO,concert);
        return concert;
    }
}
