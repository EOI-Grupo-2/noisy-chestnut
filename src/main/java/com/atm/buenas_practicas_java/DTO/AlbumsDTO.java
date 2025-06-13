package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AlbumsDTO {
    private Long id;
    private String title;
    private Double rating;
    private String spotifyLink;
    private User user;
    private Integer totalTracks;
    private LocalDateTime date;
    private String imageUrl;
}