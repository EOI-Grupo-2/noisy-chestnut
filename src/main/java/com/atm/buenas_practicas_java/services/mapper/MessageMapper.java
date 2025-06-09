package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.MessageDTO;
import com.atm.buenas_practicas_java.entities.Message;
import org.apache.maven.shared.model.fileset.Mapper;
import org.modelmapper.ModelMapper;

public class MessageMapper extends AbstractServiceMapper<Message, MessageDTO> {
    @Override
    public MessageDTO toDto(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(message, messageDTO);
        return messageDTO;
    }

    @Override
    public Message toEntity(MessageDTO messageDTO) throws Exception {
        Message message = new Message();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(messageDTO, message);
        return message;
    }
}
