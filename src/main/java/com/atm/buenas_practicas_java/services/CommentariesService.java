package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.CommentariesRepository;
import com.atm.buenas_practicas_java.services.mapper.CommentariesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentariesService extends AbstractBusinessService<Commentaries, Long, CommentariesDTO,
        CommentariesRepository, CommentariesMapper> {

    private final ConcertService concertService;
    private final UserService userService;

    public CommentariesService(CommentariesRepository repo,
                               CommentariesMapper mapper,
                               ConcertService concertService,
                               UserService userService) {
        super(repo, mapper);
        this.concertService = concertService;
        this.userService = userService;
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

    // Añadir este método a tu CommentariesService
    public CommentariesDTO addCommentToConcert(Long concertId, Long userId, String content) throws Exception {
        // Buscar concierto
        Concert concert = concertService.findById(concertId)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado"));

        // Buscar usuario
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear comentario
        CommentariesDTO commentDTO = new CommentariesDTO();
        commentDTO.setConcert(concert);
        commentDTO.setUser(user);
        commentDTO.setContent(content);
        commentDTO.setLikes(0);
        commentDTO.setDate(LocalDateTime.now());

        // Guardar y retornar
        return this.save(commentDTO);
    }

    public List<CommentariesDTO> findByPublicationId(Long id) {

        return getRepo().findByPublicationsId(id).stream()
                .map(getMapper()::toDto)
                .toList();
    }

}

