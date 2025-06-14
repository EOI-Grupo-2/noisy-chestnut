package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.FollowsRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractBusinessService<User,Long, UserDTO,
        UserRepository, UserMapper>{

    private FollowsRepository followsRepository;
    private RoleService roleService; // Ya existe

    public UserService(UserRepository userRepository, UserMapper userMapper, FollowsRepository followsRepository, RoleService roleService) {
        super(userRepository, userMapper);
        this.followsRepository = followsRepository;
        this.roleService = roleService;
    }

    public List<UserDTO> findAllUsersFollowedByUserDTO(UserDTO userDTO) throws Exception {
        return followsRepository.findByUserFollower(getMapper().toEntity(userDTO)).stream().map(follows -> getMapper().toDto(follows.getUserFollowed())).collect(Collectors.toList());
    }

    public List<UserDTO> findAllUsersFollowerByUserDTO(UserDTO userDTO) throws Exception {
        return followsRepository.findByUserFollowed(getMapper().toEntity(userDTO)).stream().map(follows -> getMapper().toDto(follows.getUserFollower())).collect(Collectors.toList());
    }

    @Transactional
    public void delete(User user){
        user.getConcerts().clear();
        user.getChats().clear();
        user.getRoles().clear();

        // Limpiar relaciones Follows bidireccionales
        for (Follows f : user.getFollowers()) {
            f.setUserFollowed(null);
        }
        for (Follows f : user.getUsersFollowed()) {
            f.setUserFollower(null);
        }
        user.getFollowers().clear();
        user.getUsersFollowed().clear();

        // Finalmente eliminar el usuario
        getRepo().delete(user);
    }

    public List<UserDTO> findUsersByRoleName(String roleName) {
        return findAllDTO().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals(roleName)))
                .collect(Collectors.toList());
    }
}
