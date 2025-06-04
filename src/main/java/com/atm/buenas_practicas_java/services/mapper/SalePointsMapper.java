package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.SalePointsDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.SalePoints;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalePointsMapper extends AbstractServiceMapper<SalePoints, SalePointsDTO> {

    public SalePointsDTO toDto(SalePoints entity) {
        if (entity == null) return null;

        SalePointsDTO dto = new SalePointsDTO();
        dto.setId(entity.getId());
        dto.setSalePointUrl(entity.getSalePointUrl());

        if (entity.getConcert() != null && entity.getConcert().getId() != null) {
            dto.setConcertId(Math.toIntExact(entity.getConcert().getId()));
        }

        dto.setTicketPrice(entity.getTicketPrice());
        return dto;
    }

    @Override
    public SalePoints toEntity(SalePointsDTO dto) {
        if (dto == null) return null;

        SalePoints entity = new SalePoints();
        entity.setId(dto.getId());
        entity.setSalePointUrl(dto.getSalePointUrl());

        if (dto.getConcertId() != null) {
            Concert concert = new Concert();
            concert.setId(dto.getConcertId().longValue());
            entity.setConcert(concert);
        }

        entity.setTicketPrice(dto.getTicketPrice());
        return entity;
    }

    @Override
    public List<SalePointsDTO> toDto(List<SalePoints> entityList) {
        return entityList == null ? List.of() : entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
