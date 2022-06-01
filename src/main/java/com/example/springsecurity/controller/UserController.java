package com.example.springsecurity.controller;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.model.UserForm;
import com.example.springsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/edit")
    String editUser() {
        return "user_edit_form";
    }

    @PostMapping("/user/edit")
    @ResponseBody
    UserDto edit(UserForm userForm) {
        return userService.userEdit(userForm);
    }
}
