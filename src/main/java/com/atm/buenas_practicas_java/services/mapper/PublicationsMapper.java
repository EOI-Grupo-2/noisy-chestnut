package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
        return publications;
    }

    @Override
    public PublicationsDTO toDto(Publications publications) {
        PublicationsDTO dto = new PublicationsDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(publications, dto);
        return dto;
    }
}