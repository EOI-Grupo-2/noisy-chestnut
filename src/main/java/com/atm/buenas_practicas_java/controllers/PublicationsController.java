package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.entities.Commentaries;
import com.atm.buenas_practicas_java.entities.Publications;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.services.CommentariesService;
import com.atm.buenas_practicas_java.services.PublicationsService;
import com.atm.buenas_practicas_java.services.UserService;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/publication")
public class PublicationsController {
    private final PublicationsService publicationsService;
    private final CommentariesService commentariesService;
    private final UserService userService;

    public PublicationsController(PublicationsService publicationsService, CommentariesService commentariesService, UserService userService, UserMapper userMapper) {
        this.publicationsService = publicationsService;
        this.commentariesService = commentariesService;
        this.userService = userService;
    }

    @GetMapping("/create-publication")
    public String showCreateForm(Model model) {
        model.addAttribute("publication", new PublicationsDTO());
        model.addAttribute("formAction", "/publication/create-publication");
        return "/new-publication";
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

    @GetMapping("/{id}")
    public String showPublicationDetails(@PathVariable Long id, Model model){
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("commentaries", commentariesService.findByPublicationId(id));
        CommentariesDTO commentariesDTO = new CommentariesDTO();
        commentariesDTO.setContent("");
        model.addAttribute("commentary", commentariesDTO);
        return "/publication/publication";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute (name= "commentary") CommentariesDTO commentariesDTO,

                             @AuthenticationPrincipal AuthUser authUser) throws Exception {
        User user = userService.findByUsernameEntity(authUser.getUsername());
        Publications publication = publicationsService.findByIdEntity(id);
        CommentariesDTO commentariesDTO2 = new CommentariesDTO();
        commentariesDTO2.setContent(commentariesDTO.getContent());
        commentariesDTO2.setPublications(publication);
        commentariesDTO2.setUser(user);
        commentariesDTO2.setDate(LocalDateTime.now());
        commentariesDTO2.setLikes(0);
        CommentariesDTO commentariesDTO3 = commentariesService.save(commentariesDTO2);
        return "redirect:/publication/" +id;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("formAction", "/publication/update/" + id);
        return "/publication/edit-publication";
    }

    @PostMapping("/update/{id}")
    public String updatePublication(@PathVariable Long id,
                                    @ModelAttribute PublicationsDTO publicationDTO,
                                    @AuthenticationPrincipal AuthUser authUser) throws Exception {
        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
        publicationDTO.setUser(userDTO);
        publicationDTO.setId(id);
        publicationsService.update(publicationDTO);
        return "redirect:/publication/" + id;
    }

    @PostMapping("/{id}/like")
    public String likePublication(@PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) throws Exception {
        Publications publication = publicationsService.findByIdEntity(id);
        if (publication == null) {
            throw new RuntimeException("Publicación no encontrada");
        }
        publication.setLikes(publication.getLikes() != null ? publication.getLikes() + 1 : 1);
        publicationsService.saveEntity(publication);
        System.out.println("Likes actuales: " + publication.getLikes()); // Log para depuración
        return "redirect:/publication/" + id;
    }


    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String deletePublication(@PathVariable Long id,
                                    @AuthenticationPrincipal AuthUser authUser) throws Exception {
        Publications publication = publicationsService.findByIdEntity(id);
        publicationsService.delete(publication);
        return "redirect:/users/" + id + "/profile";
    }

    @PostMapping("/{id}/delete-comment")
    public String deleteComment(@PathVariable Long id,
                                @AuthenticationPrincipal AuthUser authUser) throws Exception {
        Commentaries commentaries = commentariesService.findByIdEntity(id);
        if (!commentaries.getUser().getUsername().equals(authUser.getUsername())) {
            throw new RuntimeException("No puedes eliminar comentarios de otros usuarios");
        }
        Long publicationId = commentaries.getPublications().getId();
        commentariesService.deleteById(id);
        return "redirect:/publication/" + publicationId;
    }

    @GetMapping("/artists")
    public String showArtistPublications(Model model) {
        List<PublicationsDTO> artistPublications = publicationsService.findPublicationsByUserRole("ARTIST");
        model.addAttribute("publications", artistPublications);
        return "/artist/artist";
    }


}