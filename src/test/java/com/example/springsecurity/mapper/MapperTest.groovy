package com.example.springsecurity.mapper

import com.example.springsecurity.model.Person
import com.example.springsecurity.model.PersonDto
import com.example.springsecurity.model.User
import com.example.springsecurity.model.UserDto
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class MapperTest extends Specification {

    private UserMapper userMapper
    private PersonMapper personMapper

    final Long ID = 1L
    final String FIRST_NAME = "firstName"
    final String LAST_NAME = "lastName"
    final String USERNAME = "username"
    final String PASSWORD = "password"
    final boolean ACCOUNT_ACTIVATED = true

    Person person = new Person()
    User user = new User(ID, person, USERNAME, PASSWORD, ACCOUNT_ACTIVATED)
    PersonDto personDto = new PersonDto(FIRST_NAME, LAST_NAME)
    UserDto userDto = new UserDto(ID, personDto, USERNAME, PASSWORD, ACCOUNT_ACTIVATED)

    def setup() {
        userMapper = Mappers.getMapper(UserMapper.class)
        personMapper = Mappers.getMapper(PersonMapper.class)
        person.setId(ID)
        person.setFirstName(FIRST_NAME)
        person.setLastName(LAST_NAME)
    }

    def "should map a person to personDto"() {
        given:
        person.setFirstName(FIRST_NAME)
        person.setLastName(LAST_NAME)

        when:
        PersonDto dto = personMapper.mapToPersonDto(person)

        then:
        dto.getLastName() == LAST_NAME
        dto.getFirstName() == FIRST_NAME
    }

    def "should map personDto to person"() {
        when:
        Person p = personMapper.mapToPerson(personDto)

        then:
        p.getFirstName() == FIRST_NAME
        p.getLastName() == LAST_NAME
        p.getId() == null
        p.getUser() == null
    }

    def "should map a user to userDto"() {
        when:
        UserDto dto = userMapper.mapToUserDto(user)

        then:
        dto.getId() == ID
        dto.getPersonDto().getFirstName() == FIRST_NAME
        dto.getPersonDto().getLastName() == LAST_NAME
        dto.getPassword() == null
        dto.getUsername() == USERNAME
        dto.isAccountActivated()
    }

    def "should map userDto to user"() {
        when:
        User u = userMapper.mapToUser(userDto)

        then:
        u.getId() == ID
        u.getPerson().getFirstName() == FIRST_NAME
        u.getPerson().getLastName() == LAST_NAME
        u.getPerson().getUser() == u
        u.getUsername() == USERNAME
        u.getPassword() == PASSWORD
        u.isAccountActivated()
    }
}
