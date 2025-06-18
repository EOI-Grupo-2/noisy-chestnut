package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.AlbumsDTO;
import com.atm.buenas_practicas_java.entities.Albums;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class AlbumsMapper extends AbstractServiceMapper<Albums, AlbumsDTO> {

    @Override
    public Albums toEntity(AlbumsDTO dto) {
        Albums album = new Albums();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, album);
        return album;
    }

    @Override
    public AlbumsDTO toDto(Albums albums) {
        AlbumsDTO dto = new AlbumsDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(albums, dto);
        return dto;
    }
}