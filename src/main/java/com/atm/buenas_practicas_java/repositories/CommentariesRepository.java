package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Publications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentariesRepository extends JpaRepository<Commentaries, Long> {

    // Buscar comentarios por publicación (ya existente)
    List<Commentaries> findByPubOrderByDateDesc(Publications pub);

    // Buscar comentarios por concierto (nuevo)
    List<Commentaries> findByConcertOrderByDateDesc(Concert concert);
}
