package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.services.CommentariesService;
import com.atm.buenas_practicas_java.services.PublicationsService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/publication")
public class PublicationsController {
    private final PublicationsService publicationsService;
//    private final CommentariesService commentariesService;
    private final UserService userService;

    public PublicationsController(PublicationsService publicationsService, CommentariesService commentariesService, UserService userService) {
        this.publicationsService = publicationsService;
//        this.commentariesService = commentariesService;
        this.userService = userService;
    }

    @PostMapping("/create-publication")
    public String createPublication(@AuthenticationPrincipal AuthUser authUser,
                                    @ModelAttribute PublicationsDTO publicationDTO
    ) throws Exception {
        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
        publicationDTO.setUser(userDTO);
        publicationsService.save(publicationDTO);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal AuthUser authUser, Model model) throws Exception {
        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
        List<PublicationsDTO> userPublications = publicationsService.findByUserId(userDTO.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("publications", userPublications);
        return "profile";
    }
}