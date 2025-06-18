package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.AlbumsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Albums;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.AlbumsRepository;
import com.atm.buenas_practicas_java.services.mapper.AlbumsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumsService extends AbstractBusinessService<Albums,Long, AlbumsDTO,
        AlbumsRepository, AlbumsMapper> {

    public AlbumsService(AlbumsRepository albumsRepository, AlbumsMapper albumsMapper) {
        super(albumsRepository, albumsMapper);
    }

    public List<AlbumsDTO> findAllAlbumsByUser(User user) {
        return getRepo().findAllByUser(user).stream().map(albums -> getMapper().toDto(albums)).collect(Collectors.toList());
    }
}
