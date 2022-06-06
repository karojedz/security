package com.example.springsecurity.controller;

import com.example.springsecurity.model.PersonDto;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin")
    @ResponseBody
    String admin() {
        return "You are in administrator panel";
    }

    @GetMapping("/admin/edit")
    String startUserEdit() {
        return "admin_edit_form";
    }

    @PutMapping("/admin/edit")
    @ResponseBody
    UserDto editUser(UserDto userDto) {
        return adminService.editUser(userDto);
    }
}
