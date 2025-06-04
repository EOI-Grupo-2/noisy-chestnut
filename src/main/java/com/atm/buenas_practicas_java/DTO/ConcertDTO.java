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
public class ConcertDTO {
    private Long id;
    private String name;
    private String description;
    private Long placeId;
    private LocalDateTime date;
}