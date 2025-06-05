package com.atm.buenas_practicas_java.loaders;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Log4j2
@Profile("produccion")
public class ProductionlDataLoader {


    @Profile("produccionRESETDATA")
    public void loadDataProduccion() {
        log.info("Iniciando la carga de datos para el perfil de producción.");
        log.info("Datos de producción aún no definidos. Este método requiere implementación adicional.");
        log.info("Datos de entidades borrados correctamente.");
    }



}
