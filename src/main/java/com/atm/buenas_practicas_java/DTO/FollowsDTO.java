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
public class FollowsDTO {
    private Long id;
    private User userFollower;
    private User userFollowed;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
