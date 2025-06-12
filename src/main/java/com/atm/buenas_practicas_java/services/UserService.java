package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractBusinessService<User,Long, UserDTO,
        UserRepository, UserMapper>{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
    }

    public UserDTO findByUsername(String username) throws Exception {
        return this.getMapper().toDto(userRepository.findByUsername(username));
    }
}
