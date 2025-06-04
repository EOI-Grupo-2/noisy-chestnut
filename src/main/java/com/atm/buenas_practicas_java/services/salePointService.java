package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.SalePointsDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.SalePoints;
import com.atm.buenas_practicas_java.repositories.SalePointsRepository;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.services.mapper.SalePointsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalePointsService {

    private final SalePointsRepository salePointsRepository;
    private final ConcertRepository concertRepository;
    private final SalePointsMapper salePointsMapper;

    public SalePointsService(SalePointsRepository salePointsRepository,
                             ConcertRepository concertRepository,
                             SalePointsMapper salePointsMapper) {
        this.salePointsRepository = salePointsRepository;
        this.concertRepository = concertRepository;
        this.salePointsMapper = salePointsMapper;
    }

    // Obtener todos los puntos de venta
    public List<SalePointsDTO> getAllSalePoints() {
        List<SalePoints> entities = salePointsRepository.findAll();
        return salePointsMapper.toDto(entities);
    }

    // Obtener un punto de venta por ID
    public SalePointsDTO getSalePointById(Long id) {
        SalePoints entity = salePointsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de venta no encontrado con id: " + id));
        return salePointsMapper.toDto(entity);
    }

    // Crear un nuevo punto de venta
    public SalePointsDTO createSalePoint(SalePointsDTO dto) {
        SalePoints entity = salePointsMapper.toEntity(dto);
        if (dto.getConcertId() != null) {
            Optional<Concert> concertOpt = concertRepository.findById(dto.getConcertId().longValue());
            Concert concert = concertOpt.orElseThrow(() ->
                    new RuntimeException("Concierto no encontrado con id: " + dto.getConcertId()));
            entity.setConcert(concert);
        }
        SalePoints saved = salePointsRepository.save(entity);
        return salePointsMapper.toDto(saved);
    }

    // Actualizar un punto de venta existente
    public SalePointsDTO updateSalePoint(Long id, SalePointsDTO dto) {
        SalePoints entity = salePointsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Punto de venta no encontrado con id: " + id));

        entity.setSalePointUrl(dto.getSalePointUrl());
        entity.setTicketPrice(dto.getTicketPrice());

        if (dto.getConcertId() != null) {
            Optional<Concert> concertOpt = concertRepository.findById(dto.getConcertId().longValue());
            Concert concert = concertOpt.orElseThrow(() ->
                    new RuntimeException("Concierto no encontrado con id: " + dto.getConcertId()));
            entity.setConcert(concert);
        }

        SalePoints updated = salePointsRepository.save(entity);
        return salePointsMapper.toDto(updated);
    }

    // Eliminar un punto de venta
    public void deleteSalePoint(Long id) {
        salePointsRepository.deleteById(id);
    }
}