package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Place;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertMapper extends AbstractServiceMapper<Concert, ConcertDTO> {

    @Override
    public ConcertDTO toDto(Concert concert) {
        ConcertDTO concertDTO = new ConcertDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(concert, concertDTO);
        return concertDTO;
    }



    @Override
    public Concert toEntity(ConcertDTO concertDTO) throws Exception {
        Concert concert = new Concert();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(concertDTO, concert);
        return concert;
    }
}
