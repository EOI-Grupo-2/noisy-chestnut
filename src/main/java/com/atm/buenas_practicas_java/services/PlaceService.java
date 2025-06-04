package com.atm.buenas_practicas_java.services;

import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.PlaceRepository;
import com.atm.buenas_practicas_java.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Place getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Place not found with id: " + id));
    }

    public Place createPlace(Place place) {
        Long userId = place.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        place.setUser(user);
        return placeRepository.save(place);
    }

    public Place updatePlace(Long id, Place updatedPlace) {
        Place place = getPlaceById(id);
        place.setName(updatedPlace.getName());
        place.setDescription(updatedPlace.getDescription());
        place.setAddress(updatedPlace.getAddress());
        place.setCapacity(updatedPlace.getCapacity());
        place.setRating(updatedPlace.getRating());

        // Validar y asignar nuevo usuario si cambia
        Long newUserId = updatedPlace.getUser().getId();
        User user = userRepository.findById(newUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + newUserId));
        place.setUser(user);

        return placeRepository.save(place);
    }

    public void deletePlace(Long id) {
        Place place = getPlaceById(id);
        placeRepository.delete(place);
    }

    public List<Place> getPlacesByUserId(Long userId) {
        return placeRepository.findByUserId(userId);
    }
}
