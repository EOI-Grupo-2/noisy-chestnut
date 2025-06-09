package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
