package com.example.springsecurity.service;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserForm;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserService userService;

    AdminService(UserService userService) {
        this.userService = userService;
    }

    public User editUser(UserForm userForm) {
        return userService.adminEdit(userForm);
    }
}
