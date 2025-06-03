package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.RoleService;
import com.atm.buenas_practicas_java.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping({"/", "{id}/edit"})
    public String getUserEditProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findByIdDTO(id).orElse(new UserDTO()));
        model.addAttribute("isRegister", false);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "edit"})
    public String updateUserProfile(@ModelAttribute("user") UserDTO userDTO) throws Exception {
        userService.save(userDTO);
        return "redirect:/users/" + userDTO.getId() + "/profile ";
    }

    @GetMapping({"/", "/register"})
    public String getRegister(Model model) {
        model.addAttribute("isRegister", true);
        model.addAttribute("user", new UserDTO());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "register"})
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) throws Exception {
        userDTO.setRole(roleService.findByName("USER"));
        UserDTO savedUser = userService.save(userDTO);
        return "redirect:/users/" + savedUser.getId() + "/profile";
    }

    @GetMapping({"/", "{id}/profile"})
    public String getUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findByIdDTO(id).orElse(new UserDTO()));
        return "/user/profile";
    }

    @GetMapping({"/", "login"})
    public String getLogin(){
        return "/user/login";
    }

    @GetMapping({"/", "{id}/delete"})
    public String deleteUserProfile(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

}
