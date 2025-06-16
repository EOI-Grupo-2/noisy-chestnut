package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.repositories.CommentariesRepository;
import com.atm.buenas_practicas_java.services.mapper.CommentariesMapper;
import org.springframework.stereotype.Service;
EOIG2-72-Controlador-Publications
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

 desarrollo

@Service
public class CommentariesService extends AbstractBusinessService<Commentaries, Long, CommentariesDTO,
        CommentariesRepository, CommentariesMapper> {

    public CommentariesService(CommentariesRepository commentariesRepository, CommentariesMapper commentariesMapper) {
        super(commentariesRepository, commentariesMapper);
    }
 EOIG2-72-Controlador-Publications

    public List<CommentariesDTO> findByPublicationId(Long id) {

        return getRepo().findByPublicationsId(id).stream()
                .map(getMapper()::toDto)
                .toList();
    }

}


}
desarrollo
