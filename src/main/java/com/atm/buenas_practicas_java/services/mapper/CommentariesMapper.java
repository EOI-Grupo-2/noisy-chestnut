package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.entities.Commentaries;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentariesMapper extends AbstractServiceMapper<Commentaries, CommentariesDTO> {

    @Override
    public CommentariesDTO toDto(Commentaries commentaries) {
        CommentariesDTO dto = new CommentariesDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(commentaries, dto);
        return dto;
    }

    @Override
    public Commentaries toEntity(CommentariesDTO dto) throws Exception {
        Commentaries commentaries = new Commentaries();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, commentaries);
        return commentaries;
    }
}
