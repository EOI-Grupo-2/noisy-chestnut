package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}