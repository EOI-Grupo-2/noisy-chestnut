package com.atm.buenas_practicas_java.entities;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "group_id", nullable = true)
    private Group group;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Message> messages;
}