package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDTO {
    private Long id;
    private String name;
    private String description;
    private Place place;
    private LocalDateTime date;
    private String imageUrl;
    private MusicGenre musicGenre;
    private List<User> users;
    private Chat chat;
    private Concert concert;
}