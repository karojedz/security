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

    public UserRole save(UserDto userDto) {
        User user = mapper.mapToUser(userDto);
        user.setPassword(encryptPassword(user));
        UserRole userRole = createAndFillUserRole(userDto);
        userRepository.save(user);
        return userRoleRepository.save(userRole);
    }

    private String encryptPassword(User user) {
        return passwordEncoder.encode(user.getPassword());
    }

    private UserRole createAndFillUserRole(UserDto userDto) {
        UserRole userRole = new UserRole();
        userRole.setUsername(userDto.getUsername());
        userRole.setRole(Role.ROLE_USER);
        return userRole;
    }

    public UserDto adminEdit(UserDto userDto) {
        if (userRepository.existsById(userDto.getId())) {
            User user = userRepository.getById(userDto.getId());
            user = updateUser(userDto, user.getPerson());
            User saved = userRepository.save(user);
            return mapper.mapToUserDto(saved);
        }
        return null;
    }

    private User updateUser(UserDto userDto, Person person) {
        person.setFirstName(userDto.getPersonDto().getFirstName());
        person.setLastName(userDto.getPersonDto().getLastName());
        return person.getUser();
    }

    public UserDto userEdit(UserDto userDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getByUsername(username);
        user = updateUser(userDto, user.getPerson());
        User saved = userRepository.save(user);
        return mapper.mapToUserDto(saved);
    }
}
