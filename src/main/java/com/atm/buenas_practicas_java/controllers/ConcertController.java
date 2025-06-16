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

    // Mostrar lista de todos los conciertos
    @GetMapping({"", "/"})
    public String getAllConcerts(Model model) {
        model.addAttribute("concerts", concertService.findAllDTO());
        return "/concert/concerts";
    }

    // Mostrar detalles de un concierto específico - VERSIÓN ORIGINAL
    @GetMapping("/{id}")
    public String getConcertDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal AuthUser authUser) {
        ConcertDTO concert = concertService.findByIdDTO(id).orElse(new ConcertDTO());

        // Obtener puntos de venta para este concierto
        List<SalePointsDTO> salePoints = salePointsService.findAllDTO().stream()
                .filter(sp -> sp.getConcert() != null && sp.getConcert().getId().equals(id))
                .collect(Collectors.toList());

        // Obtener comentarios para este concierto
        Concert concertEntity = concertService.findById(id).orElse(null);
        List<CommentariesDTO> comments = new ArrayList<>();
        if (concertEntity != null) {
            comments = commentariesService.findByConcert(concertEntity);
        }

        // Variables para el botón asistiré
        boolean isAttending = false;
        int attendanceCount = concertService.getAttendanceCount(id);

        if (authUser != null) {
            isAttending = concertService.isUserAttending(id, authUser.getId());
        }

        // FILTRAR SOLO ARTISTAS DESDE EL BACKEND
        List<User> artists = new ArrayList<>();
        if (concert.getUsers() != null) {
            artists = concert.getUsers().stream()
                    .filter(user -> user.getRoles().stream()
                            .anyMatch(role -> role.getName().equals("ARTIST")))
                    .collect(Collectors.toList());
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

    // Añadir comentario a un concierto
    @PostMapping("/{id}/comment")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@PathVariable Long id,
                            @RequestParam String content,
                            @AuthenticationPrincipal AuthUser authUser,
                            RedirectAttributes redirectAttributes) {
        try {
            Concert concert = concertService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Concierto no encontrado"));
            User user = userService.findById(authUser.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            CommentariesDTO commentDTO = new CommentariesDTO();
            commentDTO.setConcert(concert);
            commentDTO.setUser(user);
            commentDTO.setContent(content);
            commentDTO.setLikes(0);
            commentDTO.setDate(LocalDateTime.now());

            commentariesService.save(commentDTO);

            // AÑADIR MENSAJE DE ÉXITO
            redirectAttributes.addAttribute("successMessage", "Comentario añadido correctamente");

        } catch (Exception e) {
            // AÑADIR MENSAJE DE ERROR
            redirectAttributes.addAttribute("errorMessage", "Error al añadir comentario: " + e.getMessage());
        }

        return "redirect:/concert/" + id;
    }
    // Mostrar formulario para crear nuevo concierto (solo ADMIN)
    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getNewConcertForm(Model model) {
        model.addAttribute("concert", new ConcertDTO());
        model.addAttribute("errors", new ArrayList<String>());
        addFormAttributes(model);
        return "/concert/concert-form";
    }

    // Mostrar formulario para editar concierto existente (solo ADMIN)
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditConcertForm(@PathVariable Long id, Model model) {
        ConcertDTO concert = concertService.findByIdDTO(id).orElse(null);

        // Si no existe, redirigir al admin
        if (concert == null) {
            return "redirect:/concert/admin";
        }

        model.addAttribute("concert", concert);
        model.addAttribute("errors", new ArrayList<String>());
        addFormAttributes(model);
        return "/concert/concert-form";
    }

    // Guardar concierto (crear o actualizar) (solo ADMIN)
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveConcert(@ModelAttribute("concert") ConcertDTO concertDTO,
                         @RequestParam(value = "artistIds", required = false) List<Long> artistIds,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                         @RequestParam(required = false) String ticketUrl,
                         @RequestParam(required = false) Double ticketPrice,
                         Model model) throws Exception {

        // Manejar subida de imagen
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Si es una edición y ya tenía imagen, eliminar la anterior
                if (concertDTO.getId() != null && concertDTO.getImageUrl() != null) {
                    fileUploadService.deleteImage(concertDTO.getImageUrl());
                }

                String imageUrl = fileUploadService.saveImage(imageFile);
                concertDTO.setImageUrl(imageUrl);
            } catch (IOException e) {
                List<String> errors = new ArrayList<>();
                errors.add("Error al subir la imagen: " + e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("concert", concertDTO);
                addFormAttributes(model);
                return "/concert/concert-form";
            }
        }

        // DEBE SER ASÍ (bucle for original):
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

        List<String> errors = getFormErrors(concertDTO);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("concert", concertDTO);
            addFormAttributes(model);
            return "/concert/concert-form";
        }

        // El ConcertService ahora maneja toda la lógica de relaciones
        ConcertDTO savedConcert = concertService.save(concertDTO);

        // Si hay datos de punto de venta, crear SalePoint
        if (ticketUrl != null && !ticketUrl.isEmpty() && ticketPrice != null) {
            SalePointsDTO salePointDTO = new SalePointsDTO();
            salePointDTO.setSalePointUrl(ticketUrl);
            salePointDTO.setTicketPrice(ticketPrice);
            salePointDTO.setConcert(concertService.getMapper().toEntity(savedConcert));
            salePointsService.save(salePointDTO);
        }

        return "redirect:/concert/" + savedConcert.getId();
    }

    // Eliminar concierto (solo ADMIN) - VERSIÓN ORIGINAL
    @GetMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteConcert(@PathVariable Long id) throws Exception {
        Concert concert = concertService.findById(id).orElseThrow();

        // Eliminar imagen asociada si existe
        if (concert.getImageUrl() != null) {
            fileUploadService.deleteImage(concert.getImageUrl());
        }

        // USAR EL MÉTODO SEGURO
        concertService.safeDeleteById(id);
        return "redirect:/concert/admin";
    }

    // Panel de administración de conciertos (solo ADMIN)
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminPanel(Model model) {
        model.addAttribute("concerts", concertService.findAllDTO());
        return "/concert/concertsAdmin";
    }

    // NUEVO MÉTODO PARA EL BOTÓN ASISTIRÉ
    @PostMapping("/{id}/attend")
    @PreAuthorize("isAuthenticated()")
    public String toggleAttendance(@PathVariable Long id, @AuthenticationPrincipal AuthUser authUser) throws Exception {
        concertService.toggleUserAttendance(id, authUser.getId());
        return "redirect:/concert/" + id;
    }

    // Método privado para añadir atributos necesarios al formulario
    private void addFormAttributes(Model model) {
        model.addAttribute("places", placeService.findAllDTO());
        model.addAttribute("musicGenres", MusicGenre.values());
        model.addAttribute("artists", userService.findUsersByRoleName("ARTIST")); // SOLO ARTISTAS
    }

    // Método privado para validar errores del formulario
    private List<String> getFormErrors(ConcertDTO concertDTO) {
        List<String> errors = new ArrayList<>();

        if (concertDTO.getName() == null || concertDTO.getName().isEmpty()) {
            errors.add("El nombre del concierto no puede estar vacío");
        }

        if (concertDTO.getDescription() == null || concertDTO.getDescription().isEmpty()) {
            errors.add("La descripción no puede estar vacía");
        }

        if (concertDTO.getDate() == null) {
            errors.add("La fecha del concierto es obligatoria");
        }

        if (concertDTO.getPlace() == null) {
            errors.add("El lugar del concierto es obligatorio");
        }

        // Ya no validamos imageUrl como obligatorio porque puede subirse archivo
        if (concertDTO.getMusicGenre() == null) {
            errors.add("El género musical es obligatorio");
        }

        return errors;
    }
}