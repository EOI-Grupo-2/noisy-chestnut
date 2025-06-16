package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.services.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/places")
    public ResponseEntity<List<Place>> getAllPlaces() {
        List<Place> places = placeService.findAll();
        return ResponseEntity.ok(places);
    }
    @GetMapping("/places/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id) {
        Optional<Place> place = placeService.findById(id);
        if (place.isPresent()) {
            return ResponseEntity.ok(place.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/places")
    public ResponseEntity<PlaceDTO> createPlace(@RequestBody Place place) throws Exception {
        PlaceDTO savedPlace = placeService.save(place);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlace);
    }
    @PostMapping("/places/update/{id}")
    public ResponseEntity<PlaceDTO> updatePlace(@PathVariable Long id, @ModelAttribute Place placeDetails) throws Exception {
        Optional<Place> optionalPlace = placeService.findById(id);
        if (optionalPlace.isPresent()) {
            Place existingPlace = optionalPlace.get();
            existingPlace.setName(placeDetails.getName());
            existingPlace.setDescription(placeDetails.getDescription());
            existingPlace.setAddress(placeDetails.getAddress());
            existingPlace.setCapacity(placeDetails.getCapacity());
            existingPlace.setRating(placeDetails.getRating());


            PlaceDTO updatedPlace = placeService.save(existingPlace);
            return ResponseEntity.ok(updatedPlace);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/places/delete/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        Optional<Place> optionalPlace = placeService.findById(id);
        if (optionalPlace.isPresent()) {
            placeService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}