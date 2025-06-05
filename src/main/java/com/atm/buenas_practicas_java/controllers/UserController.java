package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.RoleService;
import com.atm.buenas_practicas_java.services.UserService;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping({"/", "{id}/edit"})
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getRegister(Model model) {
        model.addAttribute("isRegister", true);
        model.addAttribute("user", new UserDTO());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "register"})
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) throws Exception {
        Role userRole = roleService.findByName("USER");
        userDTO.setRole(Set.of(userRole));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO savedUser = userService.save(userDTO);
        userRole.getUsers().add(userMapper.toEntity(savedUser));
        roleService.save(userRole);
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
