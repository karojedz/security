package com.example.springsecurity.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHandler {

    private final PasswordEncoder passwordEncoder;

    @Named("encrypt_password")
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
