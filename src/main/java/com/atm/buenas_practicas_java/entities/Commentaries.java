package com.atm.buenas_practicas_java.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commentaries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hacer opcional la relación con Publications
    @ManyToOne(fetch = FetchType.EAGER)
 EOIG2-73-Controlador-Concerts
    @JoinColumn (name = "pub_id", nullable = true)
    private Publications pub;

    @JoinColumn (name = "pub_id", nullable = false)
    private Publications publications;
 desarrollo

    // Añadir relación opcional con Concert
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "concert_id", nullable = true)
    private Concert concert;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @Column(name="content", nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer likes;

    @Column(name = "comment_date", nullable = false)
    private LocalDateTime date;
}
