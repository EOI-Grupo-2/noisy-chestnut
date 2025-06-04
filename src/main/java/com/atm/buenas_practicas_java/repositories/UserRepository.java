package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole_Name(String name);

}
