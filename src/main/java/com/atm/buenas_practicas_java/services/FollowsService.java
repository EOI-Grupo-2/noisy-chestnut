package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.FollowsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.repositories.FollowsRepository;
import com.atm.buenas_practicas_java.services.mapper.FollowsMapper;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowsService extends AbstractBusinessService<Follows, Long, FollowsDTO,
        FollowsRepository, FollowsMapper> {

    public FollowsService(FollowsRepository followsRepository, FollowsMapper followsMapper) {
        super(followsRepository, followsMapper);
    }

    public void delete(Follows follows){
        getRepo().delete(follows);
    }
}
