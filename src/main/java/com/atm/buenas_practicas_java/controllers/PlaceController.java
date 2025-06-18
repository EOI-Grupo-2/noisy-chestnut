package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.ChatDTO;
import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.entities.Place;
import com.atm.buenas_practicas_java.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;
    private final ConcertService concertService;
    private final UserService userService;
    private final FileUploadService fileUploadService;
    private final ChatService chatService;

    public PlaceController(PlaceService placeService, ConcertService concertService,
                           UserService userService, FileUploadService fileUploadService,
                           ChatService chatService) {
        this.placeService = placeService;
        this.concertService = concertService;
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.chatService = chatService;
    }

    @GetMapping("")
    public String listPlaces(Model model){
        model.addAttribute("places",placeService.findAll());
        return "/places/places";
    }

    @GetMapping("/{id}")
    public String showPlaceById(@PathVariable Long id, Model model){
        model.addAttribute("place", placeService.findByIdDTO(id).orElseThrow());
        model.addAttribute("recentConcerts", concertService.findRecentConcertsByPlaceId(id));
        return "/places/profile";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String newPlace(Model model){
        model.addAttribute("place", new PlaceDTO());
        model.addAttribute("isNew", true);
        return "/places/form";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String savePlace(@ModelAttribute("place") PlaceDTO placeDTO,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            @AuthenticationPrincipal AuthUser authUser,
                            Model model) throws Exception {

        // Manejar imagen
        handleImageUpload(placeDTO, imageFile);

        placeDTO.setRating(0.0);
        placeDTO.setUser(userService.findById(authUser.getId()).orElseThrow());
        PlaceDTO savedPlace = placeService.save(placeDTO);
        return "redirect:/places/" + savedPlace.getId();
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String editPlace(@PathVariable Long id, Model model){
        model.addAttribute("place", placeService.findByIdDTO(id).orElseThrow());
        model.addAttribute("isNew", false);
        return "/places/form";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String updatePlace(@ModelAttribute("place") PlaceDTO placeDTO,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              @AuthenticationPrincipal AuthUser authUser,
                              Model model) throws Exception {

        // Manejar imagen
        handleImageUpload(placeDTO, imageFile);

        placeDTO.setUser(userService.findById(authUser.getId()).orElseThrow());
        placeDTO.setConcerts(concertService.findConcertsByPlaceId(placeDTO.getId()));
        PlaceDTO savedPlace = placeService.save(placeDTO);
        return "redirect:/places/" + savedPlace.getId();
    }

    // Eliminar lugar
    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String deletePlace(@PathVariable Long id) throws Exception {
        Place place = placeService.findById(id).orElseThrow();

        // Eliminar imagen si existe antes de eliminar el lugar
        if (place.getImageUrl() != null && !place.getImageUrl().isEmpty()) {
            fileUploadService.deleteImage(place.getImageUrl());
        }

        placeService.deleteById(id);
        return "redirect:/places/admin";
    }

    // Panel admin
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('PLACES_ADMIN')")
    public String showPlacesAdmin(Model model) {
        List<PlaceDTO> places = placeService.findAllDTO();
        model.addAttribute("places", places);
        return "/places/placesAdmin";
    }

    // ============ MÉTODOS PRIVADOS HELPER ============

    private void handleImageUpload(PlaceDTO placeDTO, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Eliminar imagen anterior si existe y estamos editando
            if (placeDTO.getId() != null && placeDTO.getImageUrl() != null) {
                fileUploadService.deleteImage(placeDTO.getImageUrl());
            }

            // Guardar nueva imagen
            String imageUrl = fileUploadService.savePlaceImage(imageFile);
            placeDTO.setImageUrl(imageUrl);
        }
        // Si no hay archivo de imagen, mantener la URL existente (no cambiar nada)
        // Esto permite editar otros campos sin afectar la imagen actual
    }
}