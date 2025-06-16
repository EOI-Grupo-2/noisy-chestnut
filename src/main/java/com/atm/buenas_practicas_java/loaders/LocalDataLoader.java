package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.services.PublicationsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Configuration
@Log4j2
@Profile("local")
public class LocalDataLoader {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PublicationsRepository publicationsRepository;
    private final FollowsRepository followsRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConcertRepository concertRepository;
    private final PlaceRepository placeRepository;
    private final AlbumsRepository albumsRepository;

    public LocalDataLoader(UserRepository userRepository, RoleRepository roleRepository, PublicationsRepository publicationsRepository, FollowsRepository followsRepository, PasswordEncoder passwordEncoder, ConcertRepository concertRepository, PlaceRepository placeRepository, AlbumsRepository albumsRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.publicationsRepository = publicationsRepository;
        this.followsRepository = followsRepository;
        this.passwordEncoder = passwordEncoder;
        this.concertRepository = concertRepository;
        this.placeRepository = placeRepository;
        this.albumsRepository = albumsRepository;
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
        user1.setImageUrl("https://www.billboard.com/wp-content/uploads/2023/04/Eladio-Carrion-cr-Gabriel-Perez-Silva-billboard-1548.jpg");
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
        User user3 = new User();
        user3.setUsername("user2");
        user3.setPassword(passwordEncoder.encode("user3"));
        user3.setName("Artista Guapo");
        user3.setFirstName("Artista");
        user3.setLastName("Guapo");
        user3.setMusicGenre(MusicGenre.CLASSIC);
        user3.setDescription("Descripcion del artista guapo");
        user3.setRoles(Set.of(artistRole, userRole));
        user3.setGenre(Genre.MALE);
        user3.setEmail("user3@mail.com");
        userRepository.saveAll(List.of(user1, user2, user3));
        adminRole.setUsers(List.of(user1));
        userRole.setUsers(List.of(user1, user2, user3));
        artistRole.setUsers(List.of(user3));
        roleRepository.saveAll(List.of(adminRole, artistRole, concertAdminRole, placeAdminRole, userRole, anonymousRole));
        Follows follows1 = new Follows();
        follows1.setUserFollowed(user1);
        follows1.setUserFollower(user2);
        follows1.setStartDate(LocalDateTime.now());
        Follows follows2 = new Follows();
        follows2.setUserFollowed(user3);
        follows2.setUserFollower(user1);
        follows2.setStartDate(LocalDateTime.now());
        Follows follows3 = new Follows();
        follows3.setUserFollowed(user3);
        follows3.setUserFollower(user2);
        follows3.setStartDate(LocalDateTime.now());
        followsRepository.saveAll(List.of(follows1, follows2, follows3));
        user1.setFollowers(List.of(follows1));
        user1.setUsersFollowed(List.of(follows2));
        user2.setUsersFollowed(List.of(follows1, follows3));
        user3.setFollowers(List.of(follows2, follows3));
        userRepository.saveAll(List.of(user1, user2, user3));
        Publications publications1 = new Publications();
        publications1.setDate(LocalDateTime.now());
        publications1.setDescription("Descripcion del usuario chulo");
        publications1.setTitle("Title del usuario chulo");
        publications1.setLikes(0);
        publications1.setUser(user1);
        publications1.setPhotoUrl("https://www.billboard.com/wp-content/uploads/2023/04/Eladio-Carrion-cr-Gabriel-Perez-Silva-billboard-1548.jpg");
        Publications publications2 = new Publications();
        publications2.setDate(LocalDateTime.now());
        publications2.setDescription("Descripcion del usuario no chulo");
        publications2.setTitle("Title del usuario no chulo");
        publications2.setLikes(10);
        publications2.setUser(user3);
        publicationsRepository.saveAll(List.of(publications1,publications2));
        Place place1 = new Place();
        place1.setName("Place 1");
        place1.setDescription("Descripcion de la place 1");
        place1.setUser(user1);
        place1.setCapacity(30L);
        place1.setRating(4.8);
        place1.setAddress("Direccion de prueba");
        placeRepository.save(place1);
        Concert concert1 = new Concert();
        concert1.setName("Concert 1");
        concert1.setDescription("Descripcion del usuario chulo");
        concert1.setDate(LocalDateTime.now().plusDays(10));
        concert1.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiqqsyK8RwtkHlL18FOATcJL8lslPR2K0q5g&s");
        concert1.setUsers(List.of(user3, user1));
        concert1.setPlace(place1);
        concertRepository.save(concert1);
        user1.setConcerts(List.of(concert1));
        user3.setConcerts(List.of(concert1));
        userRepository.saveAll(List.of(user3, user1));
        Albums albums1 = new Albums();
        albums1.setImageUrl("https://i.etsystatic.com/45238099/r/il/79666d/5261418390/il_fullxfull.5261418390_4d6g.jpg");
        albums1.setTitle("Album 1");
        albums1.setUser(user3);
        albums1.setDate(LocalDateTime.now().minusDays(10));
        albums1.setRating(4.9);
        albums1.setTotalTracks(10);
        albums1.setSpotifyLink("https://open.spotify.com/album/3beZ5DRcWVTpXaU3ViLIF6?si=vJbiUOpUQxyeeUWRi9caQw");
        albumsRepository.save(albums1);
        log.info("Datos de entidades cargados correctamente.");
    }
}
