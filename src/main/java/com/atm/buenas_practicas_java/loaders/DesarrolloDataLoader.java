package com.atm.buenas_practicas_java.loaders;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;


@Configuration
@Log4j2
@Profile("desarrollo")
public class DesarrolloDataLoader {

@PostConstruct
public void loadDataDesarrollo() {
    log.info("Iniciando la carga de datos para el perfil desarrollo");

    log.info("Datos de entidades cargados correctamente.");

}
}
