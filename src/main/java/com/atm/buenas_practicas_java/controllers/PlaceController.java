package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.services.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("")
    public String listPlaces(Model model){
        model.addAttribute("places",placeService.findAll());
        return "/places/places";
    }
}