package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.repositories.PublicationsRepository;
import com.atm.buenas_practicas_java.services.mapper.PublicationsMapper;
import org.springframework.stereotype.Service;


@Service
public class PublicationsService extends AbstractBusinessService<Publications, Long, PublicationsDTO, PublicationsRepository, PublicationsMapper> {

    public PublicationsService(PublicationsRepository repository, PublicationsMapper mapper) {
        super(repository, mapper);
    }
}