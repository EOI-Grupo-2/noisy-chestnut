package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ChatDTO;
import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.repositories.ChatRepository;
import com.atm.buenas_practicas_java.services.mapper.ChatMapper;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends AbstractBusinessService<Chat, Long, ChatDTO, ChatRepository, ChatMapper>{

    public ChatService(ChatRepository chatRepository, ChatMapper mapper) {
        super(chatRepository, mapper);
    }
}
