package com.example.springsecurity.controller;

import com.example.springsecurity.model.PersonDto;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RegistryController {

    private final UserService userService;

    @GetMapping("/register")
    String signIn() {
        return "register_form";
    }

    @PostMapping("/register")
    String register(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return "redirect:/login";
    }
}
