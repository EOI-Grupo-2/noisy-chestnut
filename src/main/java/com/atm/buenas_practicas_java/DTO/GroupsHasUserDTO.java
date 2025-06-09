package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Group;
import com.atm.buenas_practicas_java.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupsHasUserDTO {
    private Long id;
    private User user;
    private Group group;
}
