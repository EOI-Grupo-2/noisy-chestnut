package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String message;
    private LocalDateTime date;
    private User user;
    private Chat chat;
}
