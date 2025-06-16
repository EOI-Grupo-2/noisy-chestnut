package com.atm.buenas_practicas_java.controllers;

import ch.qos.logback.core.model.Model;
import com.atm.buenas_practicas_java.DTO.FollowsDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.Follows;
import com.atm.buenas_practicas_java.entities.User;
import com.atm.buenas_practicas_java.repositories.FollowsRepository;
import com.atm.buenas_practicas_java.services.FollowsService;
import com.atm.buenas_practicas_java.services.UserService;
import com.atm.buenas_practicas_java.services.mapper.FollowsMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/follows")
public class FollowsController {
    private FollowsService followsService;
    private UserService userService;
    private FollowsMapper followsMapper;

    public FollowsController(FollowsService followsService, UserService userService, FollowsMapper followsMapper) {
        this.followsService = followsService;
        this.userService = userService;
        this.followsMapper = followsMapper;
    }

    @PostMapping("/{followerId}/{userFollowedId}")
    public String createFollow(@PathVariable Long followerId, @PathVariable Long userFollowedId, Model model) throws Exception {
        User userFollowed = userService.findById(userFollowedId).orElseThrow();
        User follower = userService.findById(followerId).orElseThrow();

        FollowsDTO follows = new FollowsDTO();
        follows.setUserFollower(follower);
        follows.setUserFollowed(userFollowed);
        follows.setStartDate(LocalDateTime.now());
        followsService.save(follows);
        return  "redirect:/users/" + userFollowedId + "/profile ";
    }
}
