package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.AlbumsDTO;
import com.atm.buenas_practicas_java.entities.Albums;
import com.atm.buenas_practicas_java.repositories.AlbumsRepository;
import com.atm.buenas_practicas_java.services.mapper.AlbumsMapper;
import org.springframework.stereotype.Service;

@Service
public class AlbumsService extends AbstractBusinessService<Albums,Long, AlbumsDTO,
        AlbumsRepository, AlbumsMapper> {

    public AlbumsService(AlbumsRepository albumsRepository, AlbumsMapper albumsMapper) {
        super(albumsRepository, albumsMapper);
    }
}
