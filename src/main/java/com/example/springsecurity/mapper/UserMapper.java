package com.example.springsecurity.mapper;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "dto.personDto", target = "person")
    @Mapping(expression = "java(true)", target = "accountActivated")
    User mapToUser(UserDto dto);

    @Mapping(source = "user.person", target = "personDto")
    @Mapping(expression = "java(null)", target = "password")
    UserDto mapToUserDto(User user);
}
