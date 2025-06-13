package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.repositories.PublicationsRepository;
import com.atm.buenas_practicas_java.services.mapper.PublicationsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PublicationsService extends AbstractBusinessService<Publications, Long, PublicationsDTO, PublicationsRepository, PublicationsMapper> {

    private final PublicationsRepository publicationsRepository;

    public PublicationsService(PublicationsRepository repository, PublicationsMapper mapper) {
        super(repository, mapper);
        this.publicationsRepository = repository;
    }

    public List<PublicationsDTO> findPublicationsOfFollowedUsers(Long userId) {
        return getRepo().findByUserFollowed(userId).stream().map(getMapper()::toDto).collect(Collectors.toList());
    }

    public void update(PublicationsDTO dto) {
        Publications publication = getMapper().toEntity(dto);
        publicationsRepository.save(publication);
    }

    public Publications findByIdEntity(Long id) {
        return publicationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Publication not found"));
    }

}
