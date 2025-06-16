package com.atm.buenas_practicas_java.services.mapper;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper  extends AbstractServiceMapper<User, UserDTO> {

    @Override
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, dto);
        dto.setFullLastName(String.format("%s %s", user.getFirstName(), user.getLastName()));
        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) throws Exception {
        User user = new User();
        ModelMapper mapper = new ModelMapper();
        mapper.map(dto, user);
        String[] lastNames = dto.getFullLastName().split(" ");
        user.setFirstName(lastNames[0]);
        user.setLastName(lastNames[1]);
        return user;
    }
}
