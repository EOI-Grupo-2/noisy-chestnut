package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.FollowsDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FollowsMapper extends AbstractServiceMapper<Follows, FollowsDTO> {

    @Override
    public FollowsDTO toDto(Follows follows) {
        FollowsDTO dto = new FollowsDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(follows, dto);
        return dto;
    }

    @Override
    public Follows toEntity(FollowsDTO dto) throws Exception {
        Follows follows = new Follows();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, follows);
        return follows;
    }
}
