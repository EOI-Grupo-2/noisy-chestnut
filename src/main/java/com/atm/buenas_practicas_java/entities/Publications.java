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
@Table(name = "publications")
public class Publications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = true)
    private String title;

    @Column (nullable = true)
    private String description;

    @Column (nullable = true)
    private String photoUrl;

    @Column(nullable = true)
    private Integer likes;

    @JoinColumn (name = "pubDate", nullable = false)
    private LocalDateTime Date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
}
