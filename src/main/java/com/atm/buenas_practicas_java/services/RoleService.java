package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.DTO.RoleDTO;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.RoleRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import com.atm.buenas_practicas_java.services.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService extends AbstractBusinessService<Role, Long, RoleDTO, RoleRepository, RoleMapper> {

    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper, UserRepository userRepository) {
        super(roleRepository, roleMapper);
        this.userRepository = userRepository;
    }

    public Role findByName(String name) {
        return getRepo().findByName(name);
    }

    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Role role = getRepo().findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    public void deleteRole(Long id) {
        if (!getRepo().existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        getRepo().deleteById(id);
    }
}
