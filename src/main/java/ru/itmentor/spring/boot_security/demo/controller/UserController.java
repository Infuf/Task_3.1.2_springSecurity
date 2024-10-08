package ru.itmentor.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {


    private UserService service;

    @GetMapping
    public String getUserData(Principal principal, Model model) {
        String name = principal.getName();
        User user = service.getUserByUsername(name);
        model.addAttribute("user", user);
        return "index";
    }
}
