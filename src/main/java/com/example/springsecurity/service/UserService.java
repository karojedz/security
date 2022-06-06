package com.example.springsecurity.service;

import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.model.*;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public UserRole save(UserDto userDto, PersonDto personDto) {
        Person person = new Person();
        User user = new User();
        UserRole userRole = new UserRole();
        fillPersonData(person, user, personDto);
        fillUserData(user, person, userDto);
        fillUserRoleData(userRole, userDto);
        userRepository.save(user);
        return userRoleRepository.save(userRole);
    }

    private boolean fillPersonData(Person person, User user, PersonDto personDto) {
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setUser(user);
        return true;
    }

    private boolean fillUserData(User user, Person person, UserDto userDto) {
        user.setPerson(person);
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAccountActivated(true);
        return true;
    }

    private boolean fillUserRoleData(UserRole userRole, UserDto userDto) {
        userRole.setUsername(userDto.getUsername());
        userRole.setRole(Role.ROLE_USER);
        return true;
    }

    public UserDto adminEdit(UserDto userDto, PersonDto personDto) {
        if (userRepository.existsById(userDto.getId())) {
            User user = userRepository.getById(userDto.getId());
            user = updateUser(personDto, user.getPerson());
            User saved = userRepository.save(user);
            return mapper.mapToUserDto(saved);
        }
        return null;
    }

    private User updateUser(PersonDto personDto, Person person) {
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        return person.getUser();
    }

    public UserDto userEdit(PersonDto personDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByUsername(username);
        user = updateUser(personDto, user.getPerson());
        User saved = userRepository.save(user);
        return mapper.mapToUserDto(saved);
    }
}
