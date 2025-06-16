package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;

@Service
public class ConcertService extends AbstractBusinessService<Concert, Long, ConcertDTO, ConcertRepository, ConcertMapper>{

    private final UserRepository userRepository;

    public ConcertService(ConcertRepository concertRepository, ConcertMapper mapper, UserRepository userRepository) {
        super(concertRepository, mapper);
        this.userRepository = userRepository;
    }

    // MÉTODOS PARA EL BOTÓN ASISTIRÉ
    public boolean isUserAttending(Long concertId, Long userId) {
        Concert concert = findById(concertId).orElse(null);
        if (concert == null) return false;

        return concert.getUsers().stream()
                .anyMatch(user -> user.getId().equals(userId) &&
                         !user.getRoles().stream().anyMatch(role -> role.getName().equals("ARTIST")));
    }

    public int getAttendanceCount(Long concertId) {
        Concert concert = findById(concertId).orElse(null);
        if (concert == null) return 0;

        return (int) concert.getUsers().stream()
                .filter(user -> !user.getRoles().stream().anyMatch(role -> role.getName().equals("ARTIST")))
                .count();
    }

    @Transactional
    public void toggleUserAttendance(Long concertId, Long userId) throws Exception {
        Concert concert = findById(concertId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        boolean isArtist = user.getRoles().stream().anyMatch(role -> role.getName().equals("ARTIST"));
        if (isArtist) {
            throw new IllegalArgumentException("Los artistas no pueden marcarse como asistentes");
        }

        if (concert.getUsers().contains(user)) {
            concert.getUsers().remove(user);
            user.getConcerts().remove(concert);
        } else {
            concert.getUsers().add(user);
            user.getConcerts().add(concert);
        }

        getRepo().save(concert);
        userRepository.save(user);
    }

    // MÉTODO SAVE PERSONALIZADO PARA MANEJAR RELACIONES
    @Override
    @Transactional
    public ConcertDTO save(ConcertDTO dto) throws Exception {
        Concert concert;

        // Si es una edición, obtener el concierto existente
        if (dto.getId() != null) {
            concert = getRepo().findById(dto.getId()).orElse(new Concert());

            // Limpiar relaciones anteriores SOLO del lado del usuario
            if (concert.getUsers() != null) {
                for (User oldUser : new ArrayList<>(concert.getUsers())) {
                    oldUser.getConcerts().remove(concert);
                    userRepository.save(oldUser);
                }
            }
        } else {
            concert = new Concert();
        }

        // Actualizar los campos básicos del concierto
        concert.setName(dto.getName());
        concert.setDescription(dto.getDescription());
        concert.setPlace(dto.getPlace());
        concert.setDate(dto.getDate());
        concert.setImageUrl(dto.getImageUrl());
        concert.setMusicGenre(dto.getMusicGenre());
        concert.setChat(dto.getChat());

        // Preparar la nueva lista de usuarios
        List<User> newUsers = new ArrayList<>();

        if (dto.getUsers() != null && !dto.getUsers().isEmpty()) {
            for (User userDto : dto.getUsers()) {
                // Obtener el usuario gestionado por JPA
                User managedUser = userRepository.findById(userDto.getId()).orElse(null);
                if (managedUser != null) {
                    newUsers.add(managedUser);

                    // Añadir este concierto a la lista del usuario si no está ya
                    if (!managedUser.getConcerts().contains(concert)) {
                        managedUser.getConcerts().add(concert);
                    }
                }
            }
        }

        // Establecer la nueva lista de usuarios en el concierto
        concert.setUsers(newUsers);

        // Guardar el concierto
        Concert savedConcert = getRepo().save(concert);

        // Guardar todos los usuarios actualizados
        for (User user : newUsers) {
            userRepository.save(user);
        }

        return getMapper().toDto(savedConcert);
    }

    // MÉTODO DE ELIMINACIÓN SEGURO
    @Transactional
    public void safeDeleteById(Long id) throws Exception {
        Concert concert = findById(id).orElseThrow();

        // Limpiar relaciones ManyToMany primero
        if (concert.getUsers() != null) {
            for (User user : new ArrayList<>(concert.getUsers())) {
                user.getConcerts().remove(concert);
                userRepository.save(user);
            }
            concert.getUsers().clear();
        }

        // Ahora eliminar el concierto
        getRepo().delete(concert);
    }

    public List<ConcertDTO> searchConcertsByName(String name) {
        List<Concert> concerts = getRepo().findByNameContainingIgnoreCase(name);
        return concerts.stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }


}