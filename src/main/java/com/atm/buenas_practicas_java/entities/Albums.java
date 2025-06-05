package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Albums {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    @Column(name = "rate", nullable = false)
    private DecimalFormat rating;

    @Column(nullable = false)
    private String spotifyLink;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer totalTracks;

    @JoinColumn(name="releaseDate", nullable = false)
    private LocalDateTime Date;

}
