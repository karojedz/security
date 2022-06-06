package com.example.springsecurity.mapper;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User mapToUser(UserDto dto);
    UserDto mapToUserDto(User user);
}
