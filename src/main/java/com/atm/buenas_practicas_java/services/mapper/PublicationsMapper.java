package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublicationsMapper extends AbstractServiceMapper<Publications, PublicationsDTO> {
    private final UserRepository userRepository;

    public PublicationsMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Publications toEntity(PublicationsDTO dto){
        Publications publications = new Publications();
        publications.setId(dto.getId());
        publications.setTitle(dto.getTitle());
        publications.setDescription(dto.getDescription());
        publications.setPhotoUrl(dto.getPhotoUrl());
        publications.setLikes(dto.getLikes());
        publications.setDate(dto.getDate());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getUserId()));
            publications.setUser(user);
        }
        return publications;
    }

    @Override
    public PublicationsDTO toDto(Publications entity) {
        if (entity == null) return null;

        PublicationsDTO dto = new PublicationsDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPhotoUrl(entity.getPhotoUrl());
        dto.setLikes(entity.getLikes());
        dto.setDate(entity.getDate());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        return dto;
    }

    @Override
    public List<Publications> toEntity(List<PublicationsDTO> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<PublicationsDTO> toDto(List<Publications> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}