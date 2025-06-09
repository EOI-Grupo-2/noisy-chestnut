package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.FollowsRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractBusinessService<User,Long, UserDTO,
        UserRepository, UserMapper>{

    private FollowsRepository followsRepository;


    public UserService(UserRepository userRepository, UserMapper userMapper, FollowsRepository followsRepository) {
        super(userRepository, userMapper);
        this.followsRepository = followsRepository;
    }

    public List<UserDTO> findAllUsersFollowedByUserDTO(UserDTO userDTO) throws Exception {
        return followsRepository.findByUserFollowed(getMapper().toEntity(userDTO)).stream().map(follows -> getMapper().toDto(follows.getUserFollowed())).collect(Collectors.toList());
    }

    public List<UserDTO> findAllUsersFollowerByUserDTO(UserDTO userDTO) throws Exception {
        return followsRepository.findByUserFollower(getMapper().toEntity(userDTO)).stream().map(follows -> getMapper().toDto(follows.getUserFollower())).collect(Collectors.toList());
    }
}
