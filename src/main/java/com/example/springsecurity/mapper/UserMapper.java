package com.example.springsecurity.mapper;

import com.example.springsecurity.model.User;
import com.example.springsecurity.model.UserDto;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses=PasswordHandler.class)
public abstract class UserMapper {

    @Autowired
    protected PasswordHandler passwordHandler;

    @Mapping(source = "dto.personDto", target = "person")
    @Mapping(expression = "java(true)", target = "accountActivated")
    @Mapping(target = "password", qualifiedByName = "encrypt_password")
    public abstract User mapToUser(UserDto dto);

    @Mapping(source = "user.person", target = "personDto")
    @Mapping(ignore = true, target = "password")
    public abstract UserDto mapToUserDto(User user);
}
