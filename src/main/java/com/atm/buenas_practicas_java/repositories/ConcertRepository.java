package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Concert;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long>, JpaSpecificationExecutor<Concert> {


    List<Concert> findByNameContainingIgnoreCase(String name);

    List<Concert> findConcertByPlaceId(Long id);

    List<Concert> findConcertByPlaceId(Long id, Sort sort);

}