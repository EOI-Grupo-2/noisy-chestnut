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
@Table(name = "ChatsMessageUser")
public class ChatUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Long idUserSender;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Long idUserReceiver;

    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime date;

    public ChatUsers() {}
}
