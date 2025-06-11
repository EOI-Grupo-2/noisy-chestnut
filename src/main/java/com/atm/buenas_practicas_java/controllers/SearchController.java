package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.DTO.SearchResponse;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.services.ConcertService;
import com.atm.buenas_practicas_java.services.PlaceService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public SearchResponse search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String concertName,
            @RequestParam(required = false) Long placeId,
            @RequestParam(required = false) String concertDate,
            @RequestParam(required = false) String placeName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Long capacity
    ) {
        List<UserDTO> users = userService.searchUsers(username, email, name);

        LocalDateTime date = null;
        if (concertDate != null && !concertDate.isEmpty()) {
            date = LocalDateTime.parse(concertDate);
        }
        List<ConcertDTO> concerts = concertService.searchConcerts(concertName, concertDate);

        List<PlaceDTO> places = placeService.searchPlaces(placeName, address);

        return new SearchResponse(users, concerts, places);
    }
}
