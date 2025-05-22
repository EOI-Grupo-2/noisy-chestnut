package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
