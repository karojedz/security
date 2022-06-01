package com.example.springsecurity.controller;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.model.UserForm;
import com.example.springsecurity.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final AdminService adminService;

    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    @ResponseBody
    String admin() {
        return "You are in administrator panel";
    }

    @GetMapping("/admin/edit")
    String startUserEdit() {
        return "admin_edit_form";
    }

    @PostMapping("/admin/edit")
    @ResponseBody
    UserDto editUser(UserForm userForm) {
        return adminService.editUser(userForm);
    }
}
