package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.SalePointsDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.SalePoints;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalePointsMapper extends AbstractServiceMapper<SalePoints, SalePointsDTO> {

    @Override
    public SalePointsDTO toDto(SalePoints salePoints) {
        SalePointsDTO dto = new SalePointsDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(salePoints, dto);
        return dto;
    }

    @Override
    public SalePoints toEntity(SalePointsDTO salePointsDTO) throws Exception {
        SalePoints salePoints = new SalePoints();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(salePointsDTO, salePoints);
        return salePoints;
    }
}
