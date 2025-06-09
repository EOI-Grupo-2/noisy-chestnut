package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.repositories.RoleRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
@Log4j2
@Profile("local")
public class LocalDataLoader {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LocalDataLoader( UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadDataLocal() {

        log.info("Iniciando la carga de datos para el perfil local");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        Role userRole = new Role();
        userRole.setName("USER");
        Role anonymousRole = new Role();
        anonymousRole.setName("ANONYMOUS");
        Role artistRole = new Role();
        artistRole.setName("ARTIST");
        Role concertAdminRole = new Role();
        concertAdminRole.setName("CONCERT_ADMIN");
        Role placeAdminRole = new Role();
        placeAdminRole.setName("PLACES_ADMIN");
        roleRepository.saveAll(List.of(adminRole, artistRole, concertAdminRole, placeAdminRole, userRole, anonymousRole));
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setName("admin");
        user1.setFirstName("admin");
        user1.setLastName("admin");
        user1.setEmail("admin@mail.com");
        user1.setGenre(Genre.MALE);
        user1.setDescription("Descripcion del admin chulo");
        user1.setMusicGenre(MusicGenre.RAP);
        user1.setRoles(Set.of(adminRole, userRole));
        User user2 = new User();
        user2.setUsername("user1");
        user2.setPassword(passwordEncoder.encode("user2"));
        user2.setName("user");
        user2.setFirstName("1");
        user2.setLastName("1");
        user2.setEmail("user@mail.com");
        user2.setGenre(Genre.MALE);
        user2.setRoles(Set.of(userRole));
        user2.setMusicGenre(MusicGenre.CLASSIC);
        user2.setDescription("Descripcion del usuario chulo");
        userRepository.saveAll(List.of(user1, user2));
        adminRole.setUsers(List.of(user1));
        userRole.setUsers(List.of(user1, user2));
        roleRepository.saveAll(List.of(adminRole, artistRole, concertAdminRole, placeAdminRole, userRole, anonymousRole));
        log.info("Datos de entidades cargados correctamente.");
    }
}
