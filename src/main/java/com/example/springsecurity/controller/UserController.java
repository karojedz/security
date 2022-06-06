package com.example.springsecurity.controller;

import com.example.springsecurity.model.PersonDto;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/edit")
    String editUser() {
        return "user_edit_form";
    }

    @PutMapping("/user/edit")
    @ResponseBody
    UserDto edit(UserDto userDto) {
        return userService.userEdit(userDto);
    }
}
