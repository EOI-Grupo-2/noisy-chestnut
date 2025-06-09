package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Concert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String title;
    private String description;
    private Concert concert;
}