package com.atm.buenas_practicas_java.loaders;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Log4j2
@Profile("alejandro")
public class AlejandroDataLoader {


    @PostConstruct
    public void loadDataLocal() {

        log.info("Iniciando la carga de datos para el perfil local");

        log.info("Datos de entidades cargados correctamente.");


    }

}
