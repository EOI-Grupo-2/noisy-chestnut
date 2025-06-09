package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.ChatDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Chat;
import com.atm.buenas_practicas_java.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper extends AbstractServiceMapper<Chat, ChatDTO> {
    @Override
    public ChatDTO toDto(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(chat, chatDTO);
        return chatDTO;
    }

    @Override
    public Chat toEntity(ChatDTO chatDTO) throws Exception {
        Chat chat = new Chat();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(chatDTO, chat);
        return chat;
    }
}
