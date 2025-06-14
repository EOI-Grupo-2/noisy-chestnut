package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.repositories.CommentariesRepository;
import com.atm.buenas_practicas_java.services.mapper.CommentariesMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentariesService extends AbstractBusinessService<Commentaries, Long, CommentariesDTO,
        CommentariesRepository, CommentariesMapper> {

    public CommentariesService(CommentariesRepository commentariesRepository, CommentariesMapper commentariesMapper) {
        super(commentariesRepository, commentariesMapper);
    }

    // Buscar comentarios por publicación
    public List<CommentariesDTO> findByPublication(Publications publication) {
        return getRepo().findByPubOrderByDateDesc(publication)
                .stream()
                .map(comment -> getMapper().toDto(comment))
                .collect(Collectors.toList());
    }

    // Buscar comentarios por concierto
    public List<CommentariesDTO> findByConcert(Concert concert) {
        return getRepo().findByConcertOrderByDateDesc(concert)
                .stream()
                .map(comment -> getMapper().toDto(comment))
                .collect(Collectors.toList());
    }
}