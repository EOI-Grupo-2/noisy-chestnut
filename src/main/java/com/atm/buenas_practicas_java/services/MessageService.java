package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.MessageDTO;
import com.atm.buenas_practicas_java.entities.Message;
import com.atm.buenas_practicas_java.repositories.MessageRepository;
import com.atm.buenas_practicas_java.services.mapper.MessageMapper;

public class MessageService extends AbstractBusinessService<Message, Long, MessageDTO, MessageRepository, MessageMapper>{

    public MessageService(MessageRepository messageRepository, MessageMapper mapper) {
        super(messageRepository, mapper);
    }
}
