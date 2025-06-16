package com.atm.buenas_practicas_java.entities;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatType type;

    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true)
    @JoinColumn(nullable = true)
    private Concert concert;

    @Column(nullable = true)
    private String groupName;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();
}