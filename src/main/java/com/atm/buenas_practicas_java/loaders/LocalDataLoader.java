package com.atm.buenas_practicas_java.loaders;

import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.repositories.*;
import com.atm.buenas_practicas_java.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

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
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public LocalDataLoader(UserRepository userRepository, RoleRepository roleRepository, PublicationsRepository publicationsRepository, FollowsRepository followsRepository, PasswordEncoder passwordEncoder, ConcertRepository concertRepository, PlaceRepository placeRepository, AlbumsRepository albumsRepository, ChatRepository chatRepository, MessageRepository messageRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.publicationsRepository = publicationsRepository;
        this.followsRepository = followsRepository;
        this.passwordEncoder = passwordEncoder;
        this.concertRepository = concertRepository;
        this.placeRepository = placeRepository;
        this.albumsRepository = albumsRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void loadDataDesarollo() {

        log.info("Iniciando la carga de datos para el perfil de producción.");

        // 1. Roles
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

        // 2. Datos para usuarios: nombres, apellidos, géneros, música
        List<String> firstNames = List.of("Laura", "Carlos", "Ana", "Miguel", "Sofía", "David", "Elena", "Javier", "María", "Luis");
        List<String> lastNames = List.of("García", "Rodríguez", "López", "Martínez", "Sánchez", "Pérez", "Gómez", "Ruiz", "Díaz", "Morales");
        List<Genre> genders = List.of(Genre.FEMALE, Genre.MALE);
        List<MusicGenre> musicGenres = List.of(MusicGenre.ROCK, MusicGenre.POP, MusicGenre.TECHNO, MusicGenre.URBAN, MusicGenre.TRAP, MusicGenre.RAP, MusicGenre.FLAMENCO, MusicGenre.CLASSIC);

        Random random = new Random();

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            String firstName = firstNames.get(i);
            String lastName = lastNames.get(i);
            user.setUsername((firstName.charAt(0) + lastName).toLowerCase());
            user.setPassword(passwordEncoder.encode("password" + (i + 1)));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setName(firstName + " " + lastName);
            user.setEmail(user.getUsername() + "@musicapp.com");
            Genre genre = genders.get(i % genders.size());
            user.setGenre(genre);

            // 50% ARTIST, 50% USER only
            boolean isArtist = (i % 2 == 0);
            if (isArtist) {
                user.setRoles(Set.of(userRole, artistRole));
                user.setMusicGenre(musicGenres.get(random.nextInt(musicGenres.size())));
                user.setDescription("Artista profesional especializado en " + user.getMusicGenre().name().toLowerCase() + ".");
            } else {
                user.setRoles(Set.of(userRole));
                user.setMusicGenre(MusicGenre.POP);
                user.setDescription("Usuario entusiasta de la música con gusto por el pop.");
            }

            // Imagen realista (randomuser.me)
            String genderPath = genre == Genre.MALE ? "men" : "women";
            user.setImageUrl("https://randomuser.me/api/portraits/" + genderPath + "/" + (i + 10) + ".jpg");

            users.add(user);
        }
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setName("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@mail.com");
        admin.setGenre(Genre.MALE);
        admin.setDescription("Descripcion del admin chulo");
        admin.setImageUrl("");
        admin.setRoles(Set.of(adminRole, userRole));
        User adminPlaces = new User();
        adminPlaces.setUsername("adminPlaces");
        adminPlaces.setPassword(passwordEncoder.encode("adminPlaces"));
        adminPlaces.setName("Admin Lugares");
        adminPlaces.setFirstName("Admin");
        adminPlaces.setLastName("Lugares");
        adminPlaces.setGenre(Genre.MALE);
        adminPlaces.setRoles(Set.of(placeAdminRole, userRole));
        adminPlaces.setEmail("adminPlaces@mail.com");
        User adminConcerts = new User();
        adminConcerts.setUsername("adminConcerts");
        adminConcerts.setPassword(passwordEncoder.encode("adminConcerts"));
        adminConcerts.setName("Admin Conciertos");
        adminConcerts.setFirstName("Admin");
        adminConcerts.setLastName("Conciertos");
        adminConcerts.setGenre(Genre.MALE);
        adminConcerts.setRoles(Set.of(concertAdminRole, userRole));
        adminConcerts.setEmail("adminConcerts@mail.com");
        users.addAll(List.of(admin, adminPlaces, adminConcerts));
        userRepository.saveAll(users);

        // 3. Lugares
        List<Place> places = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Place place = new Place();
            place.setName("Sala " + (char)('A' + i - 1));
            place.setDescription("Lugar con excelente acústica y ambiente para conciertos en vivo.");
            place.setAddress("Calle " + (100 + i) + ", Ciudad Ejemplo");
            place.setCapacity(100 + i * 25L);
            place.setRating(3.5 + i * 0.15);
            place.setUser(adminPlaces);
            place.setImageUrl("");
            places.add(place);
        }
        placeRepository.saveAll(places);

        // 4. Conciertos
        List<Concert> concerts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Concert concert = new Concert();
            concert.setName("Concierto " + (i + 1));
            concert.setDescription("Evento musical con artistas destacados del género.");
            concert.setDate(LocalDateTime.now().plusDays(7 * (i + 1)));
            concert.setImageUrl("https://picsum.photos/seed/concert" + (i + 1) + "/600/400");
            concert.setPlace(places.get(i));

            // Asignar dos artistas al concierto, elegidos aleatoriamente
            List<User> artists = new ArrayList<>();
            for (User u : users) {
                if (u.getRoles().contains(artistRole)) {
                    artists.add(u);
                }
            }
            Collections.shuffle(artists);
            List<User> concertUsers = artists.subList(0, Math.min(2, artists.size()));
            concertUsers.add(adminConcerts);
            concert.setUsers(concertUsers);

            concerts.add(concert);
        }
        concertRepository.saveAll(concerts);

        // 5. Publicaciones (3 por usuario)
        List<Publications> publications = new ArrayList<>();
        for (User user : users) {
            for (int j = 1; j <= 3; j++) {
                Publications pub = new Publications();
                pub.setUser(user);
                pub.setTitle("Publicación " + j + " de " + user.getUsername());
                pub.setDescription("Contenido de la publicación número " + j + " realizada por " + user.getName() + ".");
                pub.setDate(LocalDateTime.now().minusDays(j));
                pub.setLikes(j * 10 + random.nextInt(20));
                pub.setPhotoUrl("https://picsum.photos/seed/pub" + user.getUsername() + j + "/400/300");
                publications.add(pub);
            }
        }
        publicationsRepository.saveAll(publications);

        // Asociar conciertos a usuarios artistas
        for (User user : users) {
            if (user.getRoles().contains(artistRole)) {
                List<Concert> userConcerts = new ArrayList<>();
                for (Concert c : concerts) {
                    if (c.getUsers().contains(user)) {
                        userConcerts.add(c);
                    }
                }
                user.setConcerts(userConcerts);
            }
        }
        userRepository.saveAll(users);

        log.info("Datos de producción cargados correctamente.");
    }
}
