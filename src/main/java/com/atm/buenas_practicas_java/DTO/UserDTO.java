package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private String fullLastName;
    private String email;
    private String description;
    private Double rate;
    private Genre genre;
    private MusicGenre musicGenre;
    private Role role;
}
