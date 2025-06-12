package com.atm.buenas_practicas_java.controllers;

import org.springframework.ui.Model;
import com.atm.buenas_practicas_java.services.ConcertService;
import com.atm.buenas_practicas_java.services.PlaceService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequestMapping("/search/results")
public class SearchController {

    private final UserService userService;
    private final ConcertService concertService;
    private final PlaceService placeService;

    public SearchController(UserService userService, ConcertService concertService, PlaceService placeService) {
        this.userService = userService;
        this.concertService = concertService;
        this.placeService = placeService;
    }

    @GetMapping
    public String search(@RequestParam("query") String query,
                         @RequestParam("filter") String filter,
                         Model model) {

        var users = userService.searchUsersByName(query);
        var concerts = concertService.searchConcertsByName(query);
        var places = placeService.searchPlacesByName(query);


        // Aquí imprimes en consola para ver qué datos devuelve cada servicio
        System.out.println("Usuarios encontrados: " + users.size());
        System.out.println("Conciertos encontrados: " + concerts.size());
        System.out.println("Lugares encontrados: " + places.size());

        // Añades los resultados al modelo para que Thymeleaf los use
        model.addAttribute("users", users);
        model.addAttribute("concerts", concerts);
        model.addAttribute("places", places);
        model.addAttribute("query", query);
        model.addAttribute("filter", filter);

        switch (filter.toLowerCase()) {
            case "users":
                model.addAttribute("users", userService.searchUsersByName(query));
                model.addAttribute("concerts", Collections.emptyList());
                model.addAttribute("places", Collections.emptyList());
                model.addAttribute("type", "users");
                break;

            case "concerts":
                model.addAttribute("users", Collections.emptyList());
                model.addAttribute("concerts", concertService.searchConcertsByName(query));
                model.addAttribute("places", Collections.emptyList());
                model.addAttribute("type", "concerts");
                break;

            case "places":
                model.addAttribute("users", Collections.emptyList());
                model.addAttribute("concerts", Collections.emptyList());
                model.addAttribute("places", placeService.searchPlacesByName(query));
                model.addAttribute("type", "places");
                break;

            default:
                model.addAttribute("users", Collections.emptyList());
                model.addAttribute("concerts", Collections.emptyList());
                model.addAttribute("places", Collections.emptyList());
                model.addAttribute("type", "none");
                break;
        }

        model.addAttribute("query", query);
        model.addAttribute("filter", filter);

        return "search/search";
    }
}
