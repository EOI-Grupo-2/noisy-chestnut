package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.PublicationsRepository;
import com.atm.buenas_practicas_java.services.mapper.PublicationsMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PublicationsService extends AbstractBusinessService<Publications, Long, PublicationsDTO, PublicationsRepository, PublicationsMapper> {

    private final PublicationsRepository publicationsRepository;
    private final PublicationsMapper publicationsMapper;

    public PublicationsService(PublicationsRepository repository, PublicationsMapper mapper) {
        super(repository, mapper);
        this.publicationsRepository = repository;
        this.publicationsMapper = mapper;
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

    @Transactional
    public void delete(Publications publications) {
        publications.getUser().getPublications().remove(publications);
        publicationsRepository.delete(publications);
    }

    public Publications saveEntity(Publications publication) {
        return publicationsRepository.save(publication);
    }

    public List<PublicationsDTO> findPublicationsByUserRole(Role role) {
        List<Publications> publications = publicationsRepository.findByUserRoles(role);
        return publications.stream()
                .map(publicationsMapper::toDto)
                .collect(Collectors.toList());
    }



}
