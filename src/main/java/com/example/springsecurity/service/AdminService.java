package com.example.springsecurity.service;

import com.example.springsecurity.model.PersonDto;
import com.example.springsecurity.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    public UserDto editUser(UserDto userDto, PersonDto personDto) {
        return userService.adminEdit(userDto, personDto);
    }
}
