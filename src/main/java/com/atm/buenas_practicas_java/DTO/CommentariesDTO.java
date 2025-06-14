package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Publications;
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
public class CommentariesDTO {

    private Long id;
    private Publications pub;
    private Concert concert;
    private User user;
    private String content;
    private Integer likes;
    private LocalDateTime date;
}
