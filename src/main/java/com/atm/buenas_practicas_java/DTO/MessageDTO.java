package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private String message;
    private LocalDateTime date;
    private User user;
    private Chat chat;
}
