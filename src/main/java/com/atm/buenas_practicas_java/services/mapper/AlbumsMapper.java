package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.AlbumsDTO;
import com.atm.buenas_practicas_java.entities.Albums;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class AlbumsMapper extends AbstractServiceMapper<Albums, AlbumsDTO> {

    @Override
    public Albums toEntity(AlbumsDTO dto) {
        Albums album = new Albums();
        ModelMapper mapper = new ModelMapper();
        mapper.map(album, dto);
        album.setId(dto.getId());
        album.setTitle(dto.getTitle());
        album.setRating(new DecimalFormat(dto.getRating()));
        album.setSpotifyLink(dto.getSpotifyLink());
        album.setTotalTracks(dto.getTotalTracks());
        album.setDate(dto.getDate());
        return album;
    }

    @Override
    public AlbumsDTO toDto(Albums entity) {
        AlbumsDTO dto = new AlbumsDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setRating(entity.getRating().toPattern());
        dto.setSpotifyLink(entity.getSpotifyLink());
        dto.setTotalTracks(entity.getTotalTracks());
        dto.setDate(entity.getDate());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }
}