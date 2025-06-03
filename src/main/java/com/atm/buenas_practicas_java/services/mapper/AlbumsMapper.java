package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.AlbumsDTO;
import com.atm.buenas_practicas_java.entities.Albums;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumsMapper extends AbstractServiceMapper<Albums, AlbumsDTO> {

    @Override
    public Albums toEntity(AlbumsDTO dto) {
        Albums album = new Albums();
        album.setId(dto.getId());
        album.setTitle(dto.getTitle());
        album.setRating(new DecimalFormat(dto.getRating()));
        album.setSpotifyLink(dto.getSpotifyLink());
        album.setTotalTracks(dto.getTotalTracks());
        album.setDate(dto.getDate());
        return album;
    }
}