package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.Role;
import com.atm.buenas_practicas_java.entities.enums.Genre;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.*;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    @PreAuthorize("isAuthenticated()")
    public String getUserEditProfile(@PathVariable Long id, Model model) {
        model.addAttribute("errors", new ArrayList<String>());
        model.addAttribute("user", userService.findByIdDTO(id).orElseThrow());
        model.addAttribute("isRegister", false);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "edit"})
    @PreAuthorize("isAuthenticated()")
    public String updateUserProfile(@ModelAttribute("user") UserDTO userDTO, @AuthenticationPrincipal AuthUser authUser, Model model) throws Exception {
        List<String> errors = getFormErrors(userDTO);
        if(userDTO.getId()!=authUser.getId() || !authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()).contains("ADMIN")){
            errors.add("No se puede editar este usuario porque no posees los permisos necesarios");
        }
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            model.addAttribute("isRegister", false);
            model.addAttribute("user", userDTO);
            model.addAttribute("genres", Genre.values());
            model.addAttribute("musicGenres", MusicGenre.values());
            return "/user/editprofile";
        }
        userDTO.setRoles(roleService.findAllByUser(userMapper.toEntity(userDTO)));
        userService.save(userDTO);
        return "redirect:/users/" + userDTO.getId() + "/profile ";
    }

    @GetMapping({"/", "/register"})
    public String getRegister(Model model) {
        model.addAttribute("errors", new ArrayList<String>());
        model.addAttribute("isRegister", true);
        model.addAttribute("user", new UserDTO());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("musicGenres", MusicGenre.values());
        return "/user/editprofile";
    }

    @PostMapping({"/", "register"})
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model) throws Exception {
        List<String> errors = getFormErrors(userDTO);
        if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty() || userDTO.getPassword().length() < 8){
            errors.add("La contraseña introducida no puede estar vacía o no cumple con la longitud mínima");
        }
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            model.addAttribute("isRegister", true);
            model.addAttribute("user", userDTO);
            model.addAttribute("genres", Genre.values());
            model.addAttribute("musicGenres", MusicGenre.values());
            return "/user/editprofile";
        }
        Role userRole = roleService.findByName("USER");
        userDTO.setRoles(Set.of(userRole));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO savedUser = userService.save(userDTO);
        userRole.getUsers().add(userMapper.toEntity(savedUser));
        roleService.saveEntity(userRole);
        return "redirect:/users/" + savedUser.getId() + "/profile";
    }

    @GetMapping({"/", "{id}/profile"})
    @PreAuthorize("isAuthenticated()")
    public String getUserProfile(@PathVariable Long id, Model model) throws Exception {
        UserDTO userDTO = userService.findByIdDTO(id).orElse(new UserDTO());
        model.addAttribute("user", userDTO);
        model.addAttribute("followers", userService.findAllUsersFollowerByUserDTO(userDTO));
        model.addAttribute("usersFollowed",userService.findAllUsersFollowedByUserDTO(userDTO));
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUserProfile(@PathVariable("id") Long id) throws Exception {
        UserDTO userDTO = userService.findByIdDTO(id).orElse(new UserDTO());
        userDTO.setIsDeleted(true);
        userService.save(userDTO);
        return "redirect:/";
    }

    @GetMapping({"", "/admin"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminPanel(Model model) {
        model.addAttribute("users", userService.findAllDTO());
        return "/user/adminpanel";
    }

    private List<String> getFormErrors(UserDTO userDTO){
        List<String> errors = new ArrayList<>();
        if(userDTO.getName() == null || userDTO.getName().isEmpty()){
            errors.add("El nombre no puede estar vacío");
        }
        if(userDTO.getEmail() == null || userDTO.getEmail().isEmpty()){
            errors.add("El correo electrónico no puede estar vacío");
        }
        if(userDTO.getUsername() == null || userDTO.getUsername().isEmpty()){
            errors.add("El nombre de usuario no puede estar vacío");
        }
        if(userDTO.getFullLastName() == null || userDTO.getFullLastName().isEmpty()){
            errors.add("Los apellidos no pueden estar vacíos");
        }
        if(userDTO.getGenre() == null){
            errors.add("El género no puede ser nulo");
        }
        if(userDTO.getMusicGenre() == null){
            errors.add("El género musical no puede ser nulo");
        }
        return errors;
    }
}
