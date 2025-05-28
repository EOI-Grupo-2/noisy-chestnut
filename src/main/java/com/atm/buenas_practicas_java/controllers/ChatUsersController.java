package com.atm.buenas_practicas_java.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatUsersController {

    private final ChatUsersService chatUsersService;
    private final UserService userService;

    public ChatUsersController(ChatUsersService chatUsersService, UserService userService) {
        this.chatUsersService = chatUsersService;
        this.userService = userService;
    }

    @GetMapping({"/"})
    public String getChatUsersCreate(@PathVariable Long id, Model model) {
        model.addAttribute("user", chatUsersService.findByIdDTO(id));
        model.addAttribute("chat"
        return "/user/editprofile";
    }

}
