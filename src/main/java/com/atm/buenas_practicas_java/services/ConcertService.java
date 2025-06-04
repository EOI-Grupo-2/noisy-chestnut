package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.repositories.PlaceRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final PlaceRepository placeRepository;
    private final ConcertMapper concertMapper;

    public ConcertService(ConcertRepository concertRepository,
                          PlaceRepository placeRepository,
                          ConcertMapper concertMapper) {
        this.concertRepository = concertRepository;
        this.placeRepository = placeRepository;
        this.concertMapper = concertMapper;
    }

    // Obtener todos los conciertos
    public List<ConcertDTO> getAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return concertMapper.toDto(concerts);
    }

    // Obtener un concierto por ID
    public ConcertDTO getConcertById(Long id) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con id: " + id));
        return concertMapper.toDto(concert);
    }

    // Crear un nuevo concierto
    public ConcertDTO createConcert(ConcertDTO dto) {
        Concert concert = concertMapper.toEntity(dto);

        if (dto.getPlaceId() != null) {
            Place place = placeRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + dto.getPlaceId()));
            concert.setPlace(place);
        } else {
            throw new RuntimeException("El lugar es obligatorio para crear un concierto");
        }

        Concert saved = concertRepository.save(concert);
        return concertMapper.toDto(saved);
    }

    // Actualizar un concierto existente
    public ConcertDTO updateConcert(Long id, ConcertDTO dto) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concierto no encontrado con id: " + id));

        concert.setName(dto.getName());
        concert.setDescription(dto.getDescription());
        concert.setDate(dto.getDate());

        if (dto.getPlaceId() != null) {
            Place place = placeRepository.findById(dto.getPlaceId())
                    .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + dto.getPlaceId()));
            concert.setPlace(place);
        }

        Concert updated = concertRepository.save(concert);
        return concertMapper.toDto(updated);
    }

    // Eliminar un concierto
    public void deleteConcert(Long id) {
        concertRepository.deleteById(id);
    }
}