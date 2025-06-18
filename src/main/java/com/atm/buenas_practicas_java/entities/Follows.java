package com.atm.buenas_practicas_java.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_follower_id")
    private User userFollower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_followed_id")
    private User userFollowed;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
