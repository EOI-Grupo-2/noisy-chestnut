package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import org.springframework.ui.Model;
import com.atm.buenas_practicas_java.services.ConcertService;
import com.atm.buenas_practicas_java.services.PlaceService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final UserService userService;
    private final ConcertService concertService;
    private final PlaceService placeService;

    public SearchController(UserService userService, ConcertService concertService, PlaceService placeService) {
        this.userService = userService;
        this.concertService = concertService;
        this.placeService = placeService;
    }

    @GetMapping({"", ""})
    public String search(@RequestParam(value = "query", defaultValue = "") String query,
                         @RequestParam(value = "filter", defaultValue = "all") String filter,
                         Model model) {

        List<UserDTO> users = userService.searchUsersByName(query);
        List<ConcertDTO> concerts = concertService.searchConcertsByName(query);
        List<PlaceDTO> places = placeService.searchPlacesByName(query);

        model.addAttribute("users", users);
        model.addAttribute("concerts", concerts);
        model.addAttribute("places", places);
        model.addAttribute("query", query);
        model.addAttribute("filter", filter);

        switch (filter.toLowerCase()) {
            case "user":// coincide con el value del select
                model.addAttribute("users", userService.searchUsersByName(query));
                model.addAttribute("concerts", new ArrayList<ConcertDTO>());
                model.addAttribute("places", new ArrayList<PlaceDTO>());
                model.addAttribute("type", "user");
                break;

            case "concert":
                model.addAttribute("users", new ArrayList<UserDTO>());
                model.addAttribute("concerts", concertService.searchConcertsByName(query));
                model.addAttribute("places", new ArrayList<PlaceDTO>());
                model.addAttribute("type", "concert");
                break;

            case "place":
                model.addAttribute("users", new ArrayList<UserDTO>());
                model.addAttribute("concerts", new ArrayList<ConcertDTO>());
                model.addAttribute("places", placeService.searchPlacesByName(query));
                model.addAttribute("type", "place");
                break;

            case "all":
            case "":
                model.addAttribute("users", users);
                model.addAttribute("concerts", concerts);
                model.addAttribute("places", places);
                model.addAttribute("type", "all");
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
