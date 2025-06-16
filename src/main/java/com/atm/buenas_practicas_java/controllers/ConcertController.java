package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.CommentariesDTO;
import com.atm.buenas_practicas_java.DTO.ConcertDTO;
import com.atm.buenas_practicas_java.DTO.SalePointsDTO;
import com.atm.buenas_practicas_java.entities.AuthUser;
import com.atm.buenas_practicas_java.entities.Concert;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.entities.enums.MusicGenre;
import com.atm.buenas_practicas_java.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/concert")
public class ConcertController {

    private final ConcertService concertService;
    private final PlaceService placeService;
    private final UserService userService;
    private final SalePointsService salePointsService;
    private final FileUploadService fileUploadService;
    private final CommentariesService commentariesService;

    public ConcertController(ConcertService concertService, PlaceService placeService,
                             UserService userService, SalePointsService salePointsService,
                             FileUploadService fileUploadService, CommentariesService commentariesService) {
        this.concertService = concertService;
        this.placeService = placeService;
        this.userService = userService;
        this.salePointsService = salePointsService;
        this.fileUploadService = fileUploadService;
        this.commentariesService = commentariesService;
    }

    // Listar todos los conciertos
    @GetMapping({"", "/"})
    public String getAllConcerts(Model model) {
        model.addAttribute("concerts", concertService.findAllDTO());
        return "/concert/concerts";
    }

    // Ver detalles de un concierto
    @GetMapping("/{id}")
    public String getConcertDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal AuthUser authUser) {
        ConcertDTO concert = concertService.findByIdDTO(id).orElse(new ConcertDTO());

        // Obtener datos relacionados
        List<SalePointsDTO> salePoints = getSalePointsForConcert(id);
        List<CommentariesDTO> comments = getCommentsForConcert(id);
        List<User> artists = getArtistsFromConcert(concert);

        // Variables para asistencia
        boolean isAttending = false;
        int attendanceCount = concertService.getAttendanceCount(id);

        if (authUser != null) {
            isAttending = concertService.isUserAttending(id, authUser.getId());
        }

        model.addAttribute("concert", concert);
        model.addAttribute("salePoints", salePoints);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new CommentariesDTO());
        model.addAttribute("isAttending", isAttending);
        model.addAttribute("attendanceCount", attendanceCount);
        model.addAttribute("concertId", id);
        model.addAttribute("artists", artists);

        return "/concert/concert-detail";
    }

    // Añadir comentario
    @PostMapping("/{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@PathVariable Long id, @RequestParam String content,
                             @AuthenticationPrincipal AuthUser authUser, RedirectAttributes redirectAttributes) {
        try {
            Concert concert = concertService.findById(id).orElseThrow();
            User user = userService.findById(authUser.getId()).orElseThrow();

            CommentariesDTO commentDTO = new CommentariesDTO();
            commentDTO.setConcert(concert);
            commentDTO.setUser(user);
            commentDTO.setContent(content);
            commentDTO.setLikes(0);
            commentDTO.setDate(LocalDateTime.now());

            commentariesService.save(commentDTO);
            redirectAttributes.addAttribute("successMessage", "Comentario añadido correctamente");
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", "Error al añadir comentario: " + e.getMessage());
        }

        return "redirect:/concert/" + id;
    }

    // Formulario nuevo concierto
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getNewConcertForm(Model model) {
        model.addAttribute("errors", new ArrayList<String>());
        model.addAttribute("concert", new ConcertDTO());
        addFormAttributes(model);
        return "/concert/concert-form";
    }

    // Formulario editar concierto
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditConcertForm(@PathVariable Long id, Model model) {
        ConcertDTO concert = concertService.findByIdDTO(id).orElse(null);

        if (concert == null) {
            return "redirect:/concert/admin";
        }

        model.addAttribute("errors", new ArrayList<String>());
        model.addAttribute("concert", concert);
        addFormAttributes(model);
        return "/concert/concert-form";
    }

    // Guardar concierto
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveConcert(@ModelAttribute("concert") ConcertDTO concertDTO,
                              @RequestParam(value = "artistIds", required = false) List<Long> artistIds,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                              @RequestParam(required = false) String ticketUrl,
                              @RequestParam(required = false) Double ticketPrice,
                              Model model) throws Exception {

        // Validar errores
        List<String> errors = getFormErrors(concertDTO);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("concert", concertDTO);
            addFormAttributes(model);
            return "/concert/concert-form";
        }

        // Manejar imagen
        handleImageUpload(concertDTO, imageFile, model);

        // Asignar artistas
        assignArtistsToConcert(concertDTO, artistIds);

        // Guardar concierto
        ConcertDTO savedConcert = concertService.save(concertDTO);

        // Crear punto de venta si es necesario
        createSalePointIfNeeded(savedConcert, ticketUrl, ticketPrice);

        return "redirect:/concert/" + savedConcert.getId();
    }

    // Eliminar concierto
    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteConcert(@PathVariable Long id) throws Exception {
        Concert concert = concertService.findById(id).orElseThrow();

        if (concert.getImageUrl() != null) {
            fileUploadService.deleteImage(concert.getImageUrl());
        }

        concertService.safeDeleteById(id);
        return "redirect:/concert/admin";
    }

    // Panel admin
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminPanel(Model model) {
        model.addAttribute("concerts", concertService.findAllDTO());
        return "/concert/concertsAdmin";
    }

    // Toggle asistencia
    @PostMapping("/{id}/attend")
    @PreAuthorize("isAuthenticated()")
    public String toggleAttendance(@PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) throws Exception {
        concertService.toggleUserAttendance(id, authUser.getId());
        return "redirect:/concert/" + id;
    }

    // ============ MÉTODOS PRIVADOS HELPER ============

    private List<SalePointsDTO> getSalePointsForConcert(Long concertId) {
        return salePointsService.findAllDTO().stream()
                .filter(sp -> sp.getConcert() != null && sp.getConcert().getId().equals(concertId))
                .collect(Collectors.toList());
    }

    private List<CommentariesDTO> getCommentsForConcert(Long concertId) {
        Concert concertEntity = concertService.findById(concertId).orElse(null);
        if (concertEntity != null) {
            return commentariesService.findByConcert(concertEntity);
        }
        return new ArrayList<>();
    }

    private List<User> getArtistsFromConcert(ConcertDTO concert) {
        if (concert.getUsers() == null) {
            return new ArrayList<>();
        }

        return concert.getUsers().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals("ARTIST")))
                .collect(Collectors.toList());
    }

    private void handleImageUpload(ConcertDTO concertDTO, MultipartFile imageFile, Model model) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            // Eliminar imagen anterior si existe
            if (concertDTO.getId() != null && concertDTO.getImageUrl() != null) {
                fileUploadService.deleteImage(concertDTO.getImageUrl());
            }

            String imageUrl = fileUploadService.saveImage(imageFile);
            concertDTO.setImageUrl(imageUrl);
        }
    }

    private void assignArtistsToConcert(ConcertDTO concertDTO, List<Long> artistIds) {
        List<User> selectedArtists = new ArrayList<>();
        if (artistIds != null && !artistIds.isEmpty()) {
            for (Long artistId : artistIds) {
                User artist = userService.findById(artistId).orElse(null);
                if (artist != null) {
                    selectedArtists.add(artist);
                }
            }
        }
        concertDTO.setUsers(selectedArtists);
    }

    private void createSalePointIfNeeded(ConcertDTO savedConcert, String ticketUrl, Double ticketPrice) throws Exception {
        if (ticketUrl != null && !ticketUrl.isEmpty() && ticketPrice != null) {
            SalePointsDTO salePointDTO = new SalePointsDTO();
            salePointDTO.setSalePointUrl(ticketUrl);
            salePointDTO.setTicketPrice(ticketPrice);
            salePointDTO.setConcert(concertService.getMapper().toEntity(savedConcert));
            salePointsService.save(salePointDTO);
        }
    }

    private void addFormAttributes(Model model) {
        model.addAttribute("places", placeService.findAllDTO());
        model.addAttribute("musicGenres", MusicGenre.values());
        model.addAttribute("artists", userService.findUsersByRoleName("ARTIST"));
    }

    private List<String> getFormErrors(ConcertDTO concertDTO) {
        List<String> errors = new ArrayList<>();

        if (concertDTO.getName() == null || concertDTO.getName().isEmpty()) {
            errors.add("El nombre del concierto no puede estar vacío");
        }
        if (concertDTO.getDate() == null) {
            errors.add("La fecha del concierto es obligatoria");
        }
        if (concertDTO.getPlace() == null) {
            errors.add("El lugar del concierto es obligatorio");
        }
        if (concertDTO.getMusicGenre() == null) {
            errors.add("El género musical es obligatorio");
        }

        return errors;
    }
}