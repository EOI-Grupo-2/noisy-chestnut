package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.Publications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentariesRepository extends JpaRepository<Commentaries, Long> {

    List<Commentaries> findByPublicationsOrderByDateDesc(Publications pub);

    List<Commentaries> findByConcertOrderByDateDesc(Concert concert);

    List<Commentaries> findByPublicationsId(Long publicationsId);
}
