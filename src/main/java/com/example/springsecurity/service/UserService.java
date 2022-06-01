package com.example.springsecurity.service;

import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.model.*;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.repository.UserRoleRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private static final String ROLE_USER = "ROLE_USER";
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRole save(UserForm userForm) {
        Person person = new Person();
        User user = new User();
        UserRole userRole = new UserRole();

        person.setFirstName(userForm.getFirstName());
        person.setLastName(userForm.getLastName());
        person.setUser(user);
        user.setPerson(person);
        user.setUsername(userForm.getUsername());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);

        userRole.setUsername(userForm.getUsername());
        userRole.setRole(ROLE_USER);
        return userRoleRepository.save(userRole);
    }

    public UserDto adminEdit(UserForm userForm) {
        if (userRepository.existsById(userForm.getId())) {
            User user = userRepository.getById(userForm.getId());
            return mapper.mapToUserDto(updateUser(userForm, user));
        }
        return null;
    }

    private User updateUser(UserForm userForm, User user) {
        Person person = user.getPerson();
        person.setFirstName(userForm.getFirstName());
        person.setLastName(userForm.getLastName());
        return userRepository.save(user);
    }

    public UserDto userEdit(UserForm userForm) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByUsername(name);
        return mapper.mapToUserDto(updateUser(userForm, user));
    }
}
