package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.dtos.UserDTO;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/user/editprofile";
    }

}
