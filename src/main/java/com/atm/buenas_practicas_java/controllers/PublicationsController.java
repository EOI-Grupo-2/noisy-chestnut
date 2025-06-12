package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.services.CommentariesService;
import com.atm.buenas_practicas_java.services.PublicationsService;
import com.atm.buenas_practicas_java.services.UserService;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/publication")
public class PublicationsController {
    private final PublicationsService publicationsService;
    private final CommentariesService commentariesService;
    private final UserService userService;
    private final UserMapper userMapper;

    public PublicationsController(PublicationsService publicationsService, CommentariesService commentariesService, UserService userService, UserMapper userMapper) {
        this.publicationsService = publicationsService;
        this.commentariesService = commentariesService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/new-publication")
    public String showCreateForm(Model model) {
        model.addAttribute("publication", new PublicationsDTO());
        model.addAttribute("formAction", "/publication/create-publication");
        return "publication/new-publication";
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

    @PostMapping("/{id}")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute CommentariesDTO commentariesDTO,
                             @AuthenticationPrincipal AuthUser authUser){
        return "redirect:/publication/";
    }

    @GetMapping("/{id}")
    public String showPublicationDetails(@PathVariable Long id, Model model){
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("commentaries", commentariesService.findByPublicationId(id));
        return "/publication/publication";
    }

    @GetMapping("/new-publication/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("formAction", "/publication/new-publication/");
        return "/publication/publication";
    }

//    @PostMapping("/update/{id}")
//    public String updatePublication(@PathVariable Long id,
//                                    @ModelAttribute PublicationsDTO publicationDTO,
//                                    @AuthenticationPrincipal AuthUser authUser) throws Exception {
//        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
//        publicationDTO.setUser(userDTO);
//        publicationDTO.setId(id);
//        publicationsService.update(publicationDTO);
//        return "redirect:/publication/";
//    }


}