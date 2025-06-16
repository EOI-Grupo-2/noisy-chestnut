package com.atm.buenas_practicas_java.DTO;

import com.atm.buenas_practicas_java.entities.Message;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private ChatType type;
    private String groupName;
    private List<User> users;
    private List<Message> messages;
}
