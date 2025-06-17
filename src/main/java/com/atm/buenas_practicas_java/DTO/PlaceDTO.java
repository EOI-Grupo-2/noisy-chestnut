package com.atm.buenas_practicas_java.DTO;


import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.User;
import  com.atm.buenas_practicas_java.entities.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String imageUrl;
    private Long capacity;
    private Double rating;
    private User user;
    private List<Concert> concerts;
}
