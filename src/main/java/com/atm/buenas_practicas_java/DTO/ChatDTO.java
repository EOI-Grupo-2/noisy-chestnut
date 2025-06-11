package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Message;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.ChatType;

import java.util.List;

public class ChatDTO {
    private Long id;
    private ChatType type;
    private List<User> users;
    private List<Message> messages;
}
