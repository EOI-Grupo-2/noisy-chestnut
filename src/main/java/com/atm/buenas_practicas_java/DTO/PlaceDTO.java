package com.atm.buenas_practicas_java.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

    private Long id;
    private String name;
    private String description;
    private String address;
    private Long capacity;
    private String rating;
    private Long userId;
}
