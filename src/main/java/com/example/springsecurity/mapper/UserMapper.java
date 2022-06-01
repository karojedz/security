package com.example.springsecurity.mapper;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.model.UserForm;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User mapToUser(UserDto dto);
    UserDto mapToUserDto(User user);

    UserDto mapToUserDto(UserForm form);
}
