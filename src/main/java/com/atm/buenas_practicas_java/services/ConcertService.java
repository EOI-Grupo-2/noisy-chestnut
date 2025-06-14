package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.ConcertRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.ConcertMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConcertService extends AbstractBusinessService<Concert, Long, ConcertDTO, ConcertRepository, ConcertMapper>{

    private final UserRepository userRepository;

    public ConcertService(ConcertRepository concertRepository, ConcertMapper mapper, UserRepository userRepository) {
        super(concertRepository, mapper);
        this.userRepository = userRepository;
    }

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

        // Debug logs
        System.out.println("=== CONCIERTO GUARDADO ===");
        System.out.println("ID: " + savedConcert.getId());
        System.out.println("Nombre: " + savedConcert.getName());
        System.out.println("Artistas: " + (savedConcert.getUsers() != null ? savedConcert.getUsers().size() : 0));
        if (savedConcert.getUsers() != null) {
            savedConcert.getUsers().forEach(u -> System.out.println("- " + u.getName() + " (" + u.getId() + ")"));
        }

        return getMapper().toDto(savedConcert);
    }
}