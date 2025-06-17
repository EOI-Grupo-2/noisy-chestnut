package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {
    private Long id;
    private String title;
    private String description;
    private String photoUrl;
    private Integer likes;
    private LocalDateTime date;
    private User user;
}