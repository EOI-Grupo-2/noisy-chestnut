package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.DTO.PublicationsDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.services.CommentariesService;
import com.atm.buenas_practicas_java.services.PublicationsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/publications")
public class PublicationsController {
    private final PublicationsService publicationsService;
    private final CommentariesService commentariesService;

    public PublicationsController(PublicationsService publicationsService, CommentariesService commentariesService) {
        this.publicationsService = publicationsService;
        this.commentariesService = commentariesService;
    }

    @GetMapping({"/", "/{id}/detail"})
    public String getPublicationDetail(@PathVariable("id") Long id, Model model) {
        List<CommentariesDTO> comments = commentariesService.findByPublicationId(id);
        model.addAttribute("publication", publicationsService.findByIdDTO(id));
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new CommentariesDTO());
        return "publications/detail";
    }

    @GetMapping({"/new", "/create"})
    public String createPublicationForm(Model model) {
        model.addAttribute("publication", new PublicationsDTO());
        return "publications/create";
    }

    @PostMapping("/save")
    @PreAuthorize("isAuthenticated()")
    public String savePublication(@ModelAttribute PublicationsDTO publicationDTO,
                                  @AuthenticationPrincipal AuthUser authUser) throws Exception {
        publicationDTO.setUser(new com.atm.buenas_practicas_java.entities.User(authUser.getId()));
        publicationsService.save(publicationDTO);
        return "redirect:/";
    }

//    @GetMapping("/{id}/delete")
//      public String deletePublication(@PathVariable("id") Long id,
//                                  @AuthenticationPrincipal AuthUser authUser) {
//          publicationsService.delete(id, authUser.getId());
//      return "redirect:/publications";
//    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("@publicationsService.isOwnerOrAdmin(#id, principal.id)")
    public String editForm(@PathVariable Long id, Model model) {
        PublicationsDTO dto = publicationsService.findByIdDTO(id).orElse(null);
        if (dto == null) return "redirect:/";
        model.addAttribute("publication", dto);
        return "publications/create";
    }
}