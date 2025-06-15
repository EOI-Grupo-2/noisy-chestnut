package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicationsRepository extends JpaRepository<Publications, Long> {
    List<Publications> findByUser(User user);

    @Query("SELECT pub FROM Publications pub JOIN Follows follow ON follow.userFollower.id=?1 where pub.user.id = follow.userFollowed.id")
    List<Publications> findByUserFollowed(Long id);

    @Query("SELECT p FROM Publications p JOIN p.user u JOIN u.roles r WHERE r.name = :roleName")
    List<Publications> findByUserRoles(@Param("roleName") String roleName);

}