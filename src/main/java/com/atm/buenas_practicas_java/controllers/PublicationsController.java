package com.atm.buenas_practicas_java.controllers;


import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.DTO.PlaceDTO;
import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.services.*;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/publication")
public class PublicationsController {
    private final PublicationsService publicationsService;
    private final CommentariesService commentariesService;
    private final UserService userService;
    private final RoleService roleService;
    private final FileUploadService fileUploadService;

    public PublicationsController(PublicationsService publicationsService, CommentariesService commentariesService, UserService userService, UserMapper userMapper, RoleService roleService, FileUploadService fileUploadService) {
        this.publicationsService = publicationsService;
        this.commentariesService = commentariesService;
        this.userService = userService;
        this.roleService = roleService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/create-publication")
    @PreAuthorize("hasAuthority('USER')")
    public String showCreateForm(Model model) {
        model.addAttribute("publication", new PublicationsDTO());
        model.addAttribute("formAction", "/publication/create-publication");
        return "publication/new-publication";
    }

    @PostMapping("/create-publication")
    public String createPublication(@AuthenticationPrincipal AuthUser authUser,
                                    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,@ModelAttribute("publication") PublicationsDTO publicationDTO
    ) throws Exception {
        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
        publicationDTO.setUser(userService.getMapper().toEntity(userDTO));
        handleImageUpload(publicationDTO, imageFile);
        publicationDTO.setLikes(0);
        publicationDTO.setDate(LocalDateTime.now());
        publicationsService.save(publicationDTO);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showPublicationDetails(@PathVariable Long id, Model model){
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("commentaries", commentariesService.findByPublicationId(id));
        model.addAttribute("commentary", new CommentariesDTO());
        return "/publication/publication";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute (name= "commentary") CommentariesDTO commentariesDTO,
                             @AuthenticationPrincipal AuthUser authUser) throws Exception {
        User user = userService.findById(authUser.getId()).orElseThrow();
        PublicationsDTO publicationDTO = publicationsService.findByIdDTO(id).orElseThrow();
        CommentariesDTO commentary = new CommentariesDTO();
        commentary.setContent(commentariesDTO.getContent());
        Publications publication = publicationsService.getMapper().toEntity(publicationDTO);
        commentary.setPublications(publication);
        commentary.setUser(user);
        commentary.setDate(LocalDateTime.now());
        commentary.setLikes(0);
        commentariesService.save(commentary);
        return "redirect:/publication/" +id;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("publication", publicationsService.findByIdDTO(id).orElseThrow());
        model.addAttribute("formAction", "/publication/update/" + id);
        return "/publication/new-publication";
    }

    @PostMapping("/update/{id}")
    public String updatePublication(@PathVariable Long id,
                                    @ModelAttribute PublicationsDTO publicationDTO,
                                    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                    @AuthenticationPrincipal AuthUser authUser) throws Exception {
        UserDTO userDTO = userService.findByUsername(authUser.getUsername());
        publicationDTO.setUser(userService.getMapper().toEntity(userDTO));
        handleImageUpload(publicationDTO, imageFile);
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

    @GetMapping("/artists")
    public String showArtistPublications(Model model) {
        Role artistRole = roleService.findByName("ARTIST");
        List<PublicationsDTO> artistPublications = publicationsService.findPublicationsByUserRole(artistRole);
        model.addAttribute("publications", artistPublications);
        return "/artist/artist";
    }

    private void handleImageUpload(PublicationsDTO publicationsDTO, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            if (publicationsDTO.getId() != null && publicationsDTO.getPhotoUrl() != null) {
                fileUploadService.deleteImage(publicationsDTO.getPhotoUrl());
            }

            // Guardar nueva imagen
            String imageUrl = fileUploadService.savePublicationImage(imageFile);
            publicationsDTO.setPhotoUrl(imageUrl);
        }
    }
}