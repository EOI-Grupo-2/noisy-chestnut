package com.atm.buenas_practicas_java.entities;

import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "music_genre")
    private MusicGenre musicGenre;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;

    @ManyToMany(mappedBy = "concerts", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();
}
