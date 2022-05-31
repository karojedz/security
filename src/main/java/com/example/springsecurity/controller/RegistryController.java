package com.example.springsecurity.controller;

import com.example.springsecurity.model.UserForm;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistryController {

    private UserService userService;

    @Autowired
    RegistryController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String signIn() {
        return "register_form";
    }

    @PostMapping("/register")
    String register(UserForm userForm) {
        userService.save(userForm);
        return "redirect:/login";
    }
}
