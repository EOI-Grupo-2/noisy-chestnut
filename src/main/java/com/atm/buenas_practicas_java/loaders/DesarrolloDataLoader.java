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

import java.util.Arrays;
import java.util.List;
import java.util.Set;


@Configuration
@Log4j2
@Profile("desarrollo")
public class DesarrolloDataLoader {

    @PostConstruct
    public void loadDataDesarrollo() {

        log.info("Iniciando la carga de datos para el perfil local");
        log.info("Datos de entidades cargados correctamente.");
    }

}
