package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.repositories.CommentariesRepository;
import com.atm.buenas_practicas_java.services.mapper.CommentariesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentariesService extends AbstractBusinessService<Commentaries, Long, CommentariesDTO,
        CommentariesRepository, CommentariesMapper> {

    public CommentariesService(CommentariesRepository commentariesRepository, CommentariesMapper commentariesMapper) {
        super(commentariesRepository, commentariesMapper);
    }

    public List<CommentariesDTO> findByPublicationId(Long id) {

        return getRepo().findByPublicationsId(id).stream()
                .map(getMapper()::toDto)
                .toList();
    }

    public Commentaries findByIdEntity(Long id) {
        return getRepo().findById(id).orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    @Transactional
    public void deleteById(Long id) {
        getRepo().deleteById(id);
    }
}

