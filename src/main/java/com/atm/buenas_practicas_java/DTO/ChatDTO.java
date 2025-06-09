package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Group;
import com.atm.buenas_practicas_java.entities.Message;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import jakarta.persistence.*;

import java.util.List;

public class ChatDTO {
    private Long id;
    private ChatType type;
    private Group group;
    private List<User> users;
    private List<Message> messages;
}
