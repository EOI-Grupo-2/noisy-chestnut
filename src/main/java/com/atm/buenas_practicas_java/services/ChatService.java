package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.ChatDTO;
import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import com.atm.buenas_practicas_java.repositories.ChatRepository;
import com.atm.buenas_practicas_java.services.mapper.ChatMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService extends AbstractBusinessService<Chat, Long, ChatDTO, ChatRepository, ChatMapper>{

    public ChatService(ChatRepository chatRepository, ChatMapper mapper) {
        super(chatRepository, mapper);
    }

    public List<ChatDTO> findGroupChatsByUserId(Long id){
        return getRepo().findByTypeAndUserId(ChatType.GROUP, id).stream().map(getMapper()::toDto).collect(Collectors.toList());
    }

    public List<ChatDTO> findUsersChatsByUserId(Long id){
        return getRepo().findByTypeAndUserId(ChatType.USER, id).stream().map(getMapper()::toDto).collect(Collectors.toList());
    }
}
