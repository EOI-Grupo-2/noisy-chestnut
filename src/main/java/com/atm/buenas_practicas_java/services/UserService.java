package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import com.atm.buenas_practicas_java.specifications.UserSpecifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractBusinessService<User,Long, UserDTO,
        UserRepository, UserMapper>{


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
    }

    public List<UserDTO> searchUsers(String username, String email, String name) {
        // Aquí no debería dar error
        return getRepo().findAll(UserSpecifications.filterBy(username, email, name))
                .stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }

}



