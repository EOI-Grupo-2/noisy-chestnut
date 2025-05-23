package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "{id}/edit"})
    public String getUserEditProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findByIdDTO(id).orElse(new UserDTO()));
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "edit"})
    public String updateUserProfile(@ModelAttribute("user") UserDTO userDTO, Model model) throws Exception {
        userService.save(userDTO);
        return "redirect:/users/" + userDTO.getId() + "/edit";
    }

}
