package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String fullLastName;
    private String email;
    private String description;
    private Double rate;
    private Genre genre;
    private MusicGenre musicGenre;
    private Set<Role> roles;
    private String imageUrl;
    private List<Concert> concerts;
    private List<Chat> chats;
    private List<Follows> usersFollowed;
    private List<Follows> followers;
    private List<Publications> publications;
    private List<Albums> albums;
}
