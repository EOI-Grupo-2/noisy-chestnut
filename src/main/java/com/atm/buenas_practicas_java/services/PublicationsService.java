package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.PublicationsRepository;
import com.atm.buenas_practicas_java.services.mapper.PublicationsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PublicationsService extends AbstractBusinessService<Publications, Long, PublicationsDTO, PublicationsRepository, PublicationsMapper> {

    public PublicationsService(PublicationsRepository repository, PublicationsMapper mapper, PublicationsRepository publicationsRepository) {
        super(repository, mapper);
    }

    public List<PublicationsDTO> findPublicationsByUser(User user) {
        return getRepo().findByUser(user).stream().map(publications -> getMapper().toDto(publications)).collect(Collectors.toList());
    }
}