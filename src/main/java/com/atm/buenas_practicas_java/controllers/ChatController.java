package com.atm.buenas_practicas_java.controllers;

import com.atm.buenas_practicas_java.DTO.ChatDTO;
import com.atm.buenas_practicas_java.DTO.MessageDTO;
import com.atm.buenas_practicas_java.DTO.UserDTO;
import com.atm.buenas_practicas_java.entities.*;
import com.atm.buenas_practicas_java.entities.enums.ChatType;
import com.atm.buenas_practicas_java.services.ChatService;
import com.atm.buenas_practicas_java.services.MessageService;
import com.atm.buenas_practicas_java.services.UserService;
import com.atm.buenas_practicas_java.services.mapper.ChatMapper;
import com.atm.buenas_practicas_java.services.mapper.MessageMapper;
import com.atm.buenas_practicas_java.services.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UserMapper userMapper;
    private ChatService chatService;
    private MessageService messageService;
    private UserService userService;
    private MessageMapper messageMapper;

    public ChatController(ChatService chatService, MessageService messageService, UserService userService, MessageMapper messageMapper, UserMapper userMapper) {
        this.chatService = chatService;
        this.messageService = messageService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public String chatPage(@PathVariable Long id, Model model){
        model.addAttribute("chat", chatService.findByIdDTO(id).orElseThrow());
        model.addAttribute("newMessage", new MessageDTO());
        return "/social/chat";
    }

    @PostMapping("/{id}")
    public String sendMessage(@PathVariable Long id, @ModelAttribute("newMessage") MessageDTO messageDTO, @AuthenticationPrincipal AuthUser authUser, Model model) throws Exception {
        Chat chat = chatService.findById(id).orElseThrow();
        User user = userService.findById(authUser.getId()).orElseThrow();
        messageDTO.setId(null);
        Message message = messageMapper.toEntity(messageDTO);
        message.setChat(chat);
        message.setUser(user);
        message.setDate(LocalDateTime.now());
        messageService.saveEntity(message);
        return "redirect:/chat/" + id;
    }

    @GetMapping("")
    public String chatPage(Model model, @AuthenticationPrincipal AuthUser authUser){
        User user = userService.findById(authUser.getId()).orElseThrow();
        List<UserDTO> followers = user.getFollowers().stream().map(follow -> userService.getMapper().toDto(follow.getUserFollower())).toList();
        List<UserDTO> usersFollowed = user.getUsersFollowed().stream().map(follow -> userService.getMapper().toDto(follow.getUserFollowed())).toList();
        Set<UserDTO> users = new HashSet<>(usersFollowed);
        users.addAll(followers);
        List<User> usersWithChat = new ArrayList<>();
        user.getChats().forEach(chat -> {
            usersWithChat.addAll(chat.getUsers());
        });
        usersWithChat.stream().map(userService.getMapper()::toDto).forEach(users::remove);
        model.addAttribute("userChats", chatService.findUsersChatsByUserId(authUser.getId()));
        model.addAttribute("users", users.stream().filter(u-> !usersWithChat.stream().map(User::getId).collect(Collectors.toSet()).contains(user.getId())).collect(Collectors.toSet()));
        model.addAttribute("groupChats", chatService.findGroupChatsByUserId(authUser.getId()));
        model.addAttribute("newGroup", new ChatDTO());
        return "/social/social";
    }

    @PostMapping("/user/{id}")
    public String createUserChat(@PathVariable Long id, Model model, @AuthenticationPrincipal AuthUser authUser) throws Exception {
        User user = userService.findById(authUser.getId()).orElseThrow();
        User user2 = userService.findById(id).orElseThrow();

        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setId(null);
        chatDTO.setUsers(List.of(user, user2));
        chatDTO.setType(ChatType.USER);
        ChatDTO savedChat = chatService.save(chatDTO);
        user.getChats().add(chatService.getMapper().toEntity(savedChat));
        user2.getChats().add(chatService.getMapper().toEntity(savedChat));
        userService.saveAll(List.of(user, user2));
        return "redirect:/chat/" + savedChat.getId();
    }

    @PostMapping("/group")
    public String createGroupChat(Model model, @AuthenticationPrincipal AuthUser authUser, @ModelAttribute("newGroup") ChatDTO chatDTO) throws Exception {
        User user  = userService.findById(authUser.getId()).orElseThrow();
        chatDTO.setType(ChatType.GROUP);
        chatDTO.setUsers(List.of(user));
        ChatDTO savedChat = chatService.save(chatDTO);
        user.getChats().add(chatService.getMapper().toEntity(savedChat));
        userService.saveEntity(user);
        return "redirect:/chat/group/" + savedChat.getId();
    }

    @GetMapping("/group/{id}")
    public String createGroupChat(@PathVariable Long id, Model model, @AuthenticationPrincipal AuthUser authUser) throws Exception {
        ChatDTO group = chatService.findByIdDTO(id).orElseThrow();
        List<UserDTO> followers = userService.findByIdDTO(authUser.getId()).orElseThrow().getFollowers().stream().map(follow -> userService.getMapper().toDto(follow.getUserFollower())).toList();
        List<UserDTO> usersFollowed = userService.findByIdDTO(authUser.getId()).orElseThrow().getUsersFollowed().stream().map(follow -> userService.getMapper().toDto(follow.getUserFollowed())).toList();
        List<UserDTO> users = new ArrayList<>(usersFollowed);
        users.addAll(followers);
        model.addAttribute("group", group);
        model.addAttribute("usersInGroup", group.getUsers());
        model.addAttribute("users", users.stream().filter(user-> !group.getUsers().stream().map(User::getId).collect(Collectors.toSet()).contains(user.getId())).collect(Collectors.toSet()));
        return "/social/create-group";
    }

    @PostMapping("/group/{id}/add/{userId}")
    public String addGroupMember(@PathVariable("id") Long id, @PathVariable("userId") Long userId, Model model) throws Exception{
        ChatDTO group = chatService.findByIdDTO(id).orElseThrow();
        User user = userService.findById(userId).orElseThrow();
        group.getUsers().add(user);
        ChatDTO savedChat = chatService.save(group);
        user.getChats().add(chatService.getMapper().toEntity(savedChat));
        userService.saveEntity(user);
        return "redirect:/chat/group/" + id;
    }
}
