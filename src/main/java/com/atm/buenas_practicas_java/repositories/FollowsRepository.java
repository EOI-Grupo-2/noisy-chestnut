package com.atm.buenas_practicas_java.repositories;

import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Long> {

    List<Follows> findByUserFollower(User userFollower);

    List<Follows> findByUserFollowed(User userFollowed);

    List<Follows> findByUserFollowerAndEndDateIsNull(User userFollower);

    List<Follows> findByUserFollowedAndEndDateIsNull(User userFollowed);

    Follows findByUserFollowerAndUserFollowedAndEndDateIsNull(User userFollower, User userFollowed);
}
