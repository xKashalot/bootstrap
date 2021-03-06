package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Collection;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal User user) {
        List<User> users = userService.users();
        Collection<Role> listRoles = userService.getRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", listRoles);
        model.addAttribute("userRepo", userRepository.findByEmail(user.getEmail()));
        model.addAttribute("newUser", new User());
        return "/index";
    }


    @PostMapping("/view")
    public String showById(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

//    @PostMapping("/edit")
//    public String edit(@RequestParam("id") int id, Model model){
//    model.addAttribute("user", userService.showUser(id));
//    return "create";
//    }


//        @GetMapping("/{id}")
//    public String showById(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.showUser(id));
//        return "users/user";
//    } rest

//        @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id){
//        model.addAttribute("user", userService.showUser(id));
//        return "users/edit";
//    } rest


//        @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//        userService.update(id, user);
//        return "redirect:/users";
//    } rest

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userService.delete(id);
//        return "redirect:/users";
//    } rest

}
