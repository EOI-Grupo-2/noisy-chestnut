package com.atm.buenas_practicas_java.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private String address;

    @Column (nullable = false)
    private Long capacity;

    @Column (nullable = false)
    private String imageUrl;

    @Column(name = "rate", nullable = false)
    private Double rating;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "place", cascade = CascadeType.DETACH, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Concert> concerts = new ArrayList<>();
}
