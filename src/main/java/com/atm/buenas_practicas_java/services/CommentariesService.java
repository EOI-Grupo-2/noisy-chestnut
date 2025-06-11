package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.repositories.CommentariesRepository;
import com.atm.buenas_practicas_java.services.mapper.CommentariesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentariesService extends AbstractBusinessService<Commentaries, Long, CommentariesDTO,
        CommentariesRepository, CommentariesMapper> {

    public CommentariesService(CommentariesRepository commentariesRepository, CommentariesMapper commentariesMapper) {
        super(commentariesRepository, commentariesMapper);
    }

    public List<CommentariesDTO> findByPublicationId(Long publicationId) {
        return getRepo().findByPublicationId(publicationId).stream().map(getMapper()::toDto).toList();
    }
}