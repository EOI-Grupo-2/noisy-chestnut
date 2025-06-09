package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.entities.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controlador encargado de manejar las solicitudes relacionadas con la entidad principal.
 *
 * Este controlador utiliza la anotación {@code @Controller} para ser detectado como un componente
 * Spring MVC y maneja solicitudes HTTP. Su objetivo principal es gestionar las operaciones
 * necesarias para mostrar una lista de entidades en la vista correspondiente.
 *
 * Anotaciones importantes:
 * - {@code @Controller}: Indica que esta clase se comporta como un controlador Spring MVC.
 * - {@code @PreAuthorize}: Define que el acceso a ciertos métodos esté restringido
 *   según las reglas de autorización establecidas.
 *
 * Dependencias:
 * - {@code EntidadPadreRepository}: Interfaz del repositorio que permite interactuar con
 *   la base de datos para operaciones de persistencia y consulta relacionadas con
 *   la entidad padre.
 *
 * Métodos principales:
 * - {@code listEntities}: Maneja solicitudes GET a la URL "/entities", recupera los
 *   datos de las entidades desde la base de datos y los pasa al modelo para mostrarlos
 *   en una vista.
 *
 */
@Controller
public class DefaultController {

    @GetMapping({"", "/"})
    public String getHomePage() {
        return "index";
    }

    @GetMapping({"", "/concerts"})
    public String getConcertPage(){
        return "/concert/concerts";
    }

    @GetMapping({"", "/artists"})
    public String getArtistPage(){
        return "/artist/artist";
    }
  

    @GetMapping({"", "/new-publication"})
    public String getNewPublicationPage(){
        return "/publication/new-publication";
    }

    @GetMapping({"", "/chat"})
    public String getSocialPage(){
        return "/social/social";
    }
  
    @GetMapping({"", "/register"})
    public String getRegister(){
        return "/user/register";
    }
  
    @GetMapping({"", "/user/profile"})
    public String getUserProfile(){
        return "/user/profile";
    }

    @GetMapping({"", "/user/editprofile"})
    public String getUserEditProfile(){
        return "/user/editprofile";
    }

    @GetMapping({"", "/user/adminpanel"})
    public String getAdminPanel(){
        return "/user/adminpanel";
    }


    @GetMapping({"", "/artist/profile"})
    public String getArtistProfile(){
        return "/artist/profile";
    }
  
    @GetMapping({"", "/chat/id"})
    public String getChatPage(){
        return "/social/chat";
    }

    @GetMapping({"", "/search"})
    public String getSearch(){
        return "/search/search";
    }

    @GetMapping({"", "/places/new"})
    public String createPlacePage(){
        return "/places/form";
    }

    @GetMapping({"", "/search/concerts"})
    public String getSearchConcertsPage(){
        return "/search/concerts";
    }

    @GetMapping({"", "/places/id"})
    public String getPlacesProfile(){
        return "/places/profile";
    }

    @GetMapping({"", "/places/edit"})
    public String editPlacePage(){
        return "/places/form";
    }

    @GetMapping({"", "/publication"})
    public String getPublicationPage(){return "/publication/publication";}
  
    @GetMapping("/concert")
    public String showConcertsPage() {
        return "concert/concerts";
    }

    @GetMapping("/concert/detail")
    public String showConcertDetailPage() {
        return "concert/concert-detail";
    }

    @GetMapping("/concert/form")
    public String showConcertFormPage() {
        return "concert/concert-form";
    }


    @GetMapping("/places/admin")
    public String showPlacesAdminPage() {
        return "places/placesAdmin";
    }


    @GetMapping("/places")
    public String showPlacesPage() {return "/places/places"; }

    @GetMapping("/concert/admin")
    public String showConcertsAdminPage() {return "concert/concertsAdmin"; }
}

