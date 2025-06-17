package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.SalePointsDTO;
import com.atm.buenas_practicas_java.entities.SalePoints;
import com.atm.buenas_practicas_java.repositories.SalePointsRepository;
import com.atm.buenas_practicas_java.services.mapper.SalePointsMapper;
import org.springframework.stereotype.Service;

@Service
public class SalePointsService extends AbstractBusinessService<SalePoints, Long, SalePointsDTO, SalePointsRepository, SalePointsMapper>{

    protected SalePointsService(SalePointsRepository salePointsRepository, SalePointsMapper mapper) {
        super(salePointsRepository, mapper);
    }
}