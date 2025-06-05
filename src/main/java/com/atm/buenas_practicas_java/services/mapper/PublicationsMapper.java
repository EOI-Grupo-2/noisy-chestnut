package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, publications);
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getUserId()));
            publications.setUser(user);
        }
        return publications;
    }

    @Override
    public PublicationsDTO toDto(Publications publications) {
        if (publications == null) return null;
        PublicationsDTO dto = new PublicationsDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(publications, dto);
        if (publications.getUser() != null) {
            dto.setUserId(publications.getUser().getId());
        }
        return dto;
    }
}