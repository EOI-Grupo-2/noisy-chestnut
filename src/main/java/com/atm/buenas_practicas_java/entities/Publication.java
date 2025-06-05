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
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private String photoUrl;

    @Column(nullable = false)
    private Integer likes;

    @JoinColumn (name = "pubDate", nullable = false)
    private LocalDateTime Date;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;
}
