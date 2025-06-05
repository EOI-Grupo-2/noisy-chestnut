package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.PublicationsRepository;
import com.atm.buenas_practicas_java.services.mapper.PublicationsMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicationsService extends AbstractBusinessService<Publications, Long, PublicationsDTO, PublicationsRepository, PublicationsMapper> {

    public PublicationsService(PublicationsRepository repository, PublicationsMapper mapper) {
        super(repository, mapper);
    }

    public PublicationsDTO createPublicationWithUser(PublicationsDTO dto, User user) {
        Publications entity = this.getMapper().toEntity(dto);
        entity.setUser(user);
        Publications saved = this.getRepo().save(entity);
        return this.getMapper().toDto(saved);
    }

    public java.util.List<PublicationsDTO> findByUser(User user) {
        return this.getMapper().toDto(this.getRepo().findByUser(user));
    }

    public boolean deleteIfOwnedBy(Long pubId, Long userId) {
        Optional<Publications> optional = this.getRepo().findById(pubId);
        if (optional.isPresent()) {
            Publications pub = optional.get();
            if (pub.getUser().getId().equals(userId)) {
                this.getRepo().delete(pub);
                return true;
            }
        }
        return false;
    }
}