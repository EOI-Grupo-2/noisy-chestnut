package com.atm.buenas_practicas_java.DTO;

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
    private String rating;
    private String spotifyLink;
    private Long userId;
    private Integer totalTracks;
    private LocalDateTime date;
}