package com.example.springsecurity.mapper;

import com.example.springsecurity.model.Person;
import com.example.springsecurity.model.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto mapToPersonDto(Person person);
    Person mapToPerson(PersonDto personDto);
}
