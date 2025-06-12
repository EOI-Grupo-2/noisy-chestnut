package com.atm.buenas_practicas_java.controllers;

import org.springframework.ui.Model;
import com.atm.buenas_practicas_java.services.ConcertService;
import com.atm.buenas_practicas_java.services.PlaceService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                         Model model) {

        model.addAttribute("users", userService.searchUsersByName(query));
        model.addAttribute("concerts", concertService.searchConcertsByName(query));
        model.addAttribute("places", placeService.searchPlacesByName(query));
        model.addAttribute("query", query);

        return "search/search";
    }
}

