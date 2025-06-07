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
public class FollowsDTO {

    private Long id;
    //Simplificación de relaciones: En lugar de incluir objetos User completos, incluimos:
    //userFollowerId y userFollowedId: Los IDs de los usuarios para las relaciones de seguidores y seguidos.
    private Long userFollowerId;
    private Long userFollowedId;
    //userFollowerUsername y userFollowedUsername: Los nombres de usuario para mostrar información básica sin cargar objetos completos
    private String userFollowerUsername;
    private String userFollowedUsername;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
