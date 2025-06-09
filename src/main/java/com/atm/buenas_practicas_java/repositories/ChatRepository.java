package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
