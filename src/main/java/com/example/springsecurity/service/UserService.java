package com.example.springsecurity.service;

import com.example.springsecurity.model.Person;
import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserForm;
import com.example.springsecurity.model.UserRole;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.repository.UserRoleRepository;
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

    @Autowired
    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserForm userForm) {
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
        userRoleRepository.save(userRole);
    }

    public User adminEdit(UserForm userForm) {
        if (userRepository.existsById(userForm.getId())) {
            User user = userRepository.getById(userForm.getId());
            return updateUser(userForm, user);
        }
        return null;
    }

    private User updateUser(UserForm userForm, User user) {
        Person person = user.getPerson();
        person.setFirstName(userForm.getFirstName());
        person.setLastName(userForm.getLastName());
        return userRepository.save(user);
    }

    public User userEdit(UserForm userForm) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByUsername(name);
        return updateUser(userForm, user);
    }
}
