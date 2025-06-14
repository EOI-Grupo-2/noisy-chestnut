package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import org.springframework.stereotype.Service;

@Service
public class ConcertMapper extends AbstractServiceMapper<Concert, ConcertDTO> {

    @Override
    public ConcertDTO toDto(Concert concert) {
        if (concert == null) return null;

        ConcertDTO concertDTO = new ConcertDTO();
        concertDTO.setId(concert.getId());
        concertDTO.setName(concert.getName());
        concertDTO.setDescription(concert.getDescription());
        concertDTO.setPlace(concert.getPlace());
        concertDTO.setDate(concert.getDate());
        concertDTO.setImageUrl(concert.getImageUrl());
        concertDTO.setMusicGenre(concert.getMusicGenre());
        concertDTO.setUsers(concert.getUsers()); // Esto es clave
        concertDTO.setChat(concert.getChat());

        // Debug
        System.out.println("=== MAPPER TO DTO ===");
        System.out.println("Concert ID: " + concert.getId());
        System.out.println("Users en entity: " + (concert.getUsers() != null ? concert.getUsers().size() : 0));
        System.out.println("Users en DTO: " + (concertDTO.getUsers() != null ? concertDTO.getUsers().size() : 0));

        return concertDTO;
    }

    @Override
    public Concert toEntity(ConcertDTO concertDTO) throws Exception {
        if (concertDTO == null) return null;

        Concert concert = new Concert();
        concert.setId(concertDTO.getId());
        concert.setName(concertDTO.getName());
        concert.setDescription(concertDTO.getDescription());
        concert.setPlace(concertDTO.getPlace());
        concert.setDate(concertDTO.getDate());
        concert.setImageUrl(concertDTO.getImageUrl());
        concert.setMusicGenre(concertDTO.getMusicGenre());
        concert.setUsers(concertDTO.getUsers());
        concert.setChat(concertDTO.getChat());

        return concert;
    }
}
