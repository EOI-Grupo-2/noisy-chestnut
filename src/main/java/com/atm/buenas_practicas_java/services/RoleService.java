package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.RoleRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private  UserRepository userRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }


    // Listar todos los roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Obtener usuarios por nombre de rol
    public List<User> getUsersByRoleName(String roleName) {
        return userRepository.findByRole_Name(roleName);
    }


    // Asignar rol a un usuario
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRole(role);
        return userRepository.save(user);
    }

    // 5. Eliminar un rol
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Rol no encontrado");
        }
        roleRepository.deleteById(id);
    }

}

