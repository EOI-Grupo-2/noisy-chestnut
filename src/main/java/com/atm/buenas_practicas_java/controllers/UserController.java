package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.*;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PublicationsService publicationsService;
    private final AlbumsService albumsService;

    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper, PublicationsService publicationsService, FollowsService followsService, AlbumsService albumsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.publicationsService = publicationsService;
        this.albumsService = albumsService;
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
        userDTO.setRoles(Set.of(userRole));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO savedUser = userService.save(userDTO);
        userRole.getUsers().add(userMapper.toEntity(savedUser));
        roleService.saveEntity(userRole);
        return "redirect:/users/" + savedUser.getId() + "/profile";
    }

    @GetMapping({"/", "{id}/profile"})
    public String getUserProfile(@PathVariable Long id, Model model) throws Exception {
        UserDTO userDTO = userService.findByIdDTO(id).orElse(new UserDTO());
        model.addAttribute("user", userDTO);
        model.addAttribute("followers", userService.findAllUsersFollowedByUserDTO(userDTO));
        model.addAttribute("usersFollowed",userService.findAllUsersFollowerByUserDTO(userDTO));
        model.addAttribute("publications", publicationsService.findPublicationsByUser(userMapper.toEntity(userDTO)));
        model.addAttribute("concerts", userDTO.getConcerts());

        Boolean isArtist = false;
        for(Role role : userDTO.getRoles()) {
            if (role.getName().equals("ARTIST")) {
                isArtist = true;
                break;
            }
        }
        if (isArtist) {
            model.addAttribute("albums", albumsService.findAllAlbumsByUser(userMapper.toEntity(userDTO)));
        }
        model.addAttribute("isArtist", isArtist);
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
