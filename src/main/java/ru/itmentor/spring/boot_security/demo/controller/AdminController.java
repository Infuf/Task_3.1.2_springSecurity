package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "all_users";
    }

    @GetMapping("/user/new")
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:all";
    }

    @GetMapping("/user/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.remove(id);
        return "redirect:/admin/all";
    }

    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit_user";
    }

    @PostMapping("/user/{id}")
    public String addUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {

        User existingUser = userService.getUser(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setUserName(user.getUserName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        userService.update(user);
        return "redirect:/admin/all";
    }
}
