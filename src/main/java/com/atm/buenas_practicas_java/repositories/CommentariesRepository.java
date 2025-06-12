package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Commentaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentariesRepository extends JpaRepository<Commentaries, Long> {

    List<Commentaries> findByPublicationsId(Long publicationsId);
}
