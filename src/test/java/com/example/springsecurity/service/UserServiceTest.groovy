package com.example.springsecurity.service

import com.example.springsecurity.mapper.UserMapper
import com.example.springsecurity.model.Person
import com.example.springsecurity.model.PersonDto
import com.example.springsecurity.model.Role
import com.example.springsecurity.model.User
import com.example.springsecurity.model.UserDto
import com.example.springsecurity.model.UserRole
import com.example.springsecurity.repository.UserRepository
import com.example.springsecurity.repository.UserRoleRepository
import org.mapstruct.factory.Mappers
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    private UserService userService;

    UserRepository userRepository = Mock()
    UserRoleRepository userRoleRepository = Mock()
    PasswordEncoder encoder = Mock()

    UserMapper mapper

    final Long ID = 1L
    final String FIRST_NAME = "firstName"
    final String LAST_NAME = "lastName"
    final String USERNAME = "username"
    final String PASSWORD = "password"
    final boolean ACCOUNT_ACTIVATED = false
    final Role ROLE = Role.ROLE_USER

    PersonDto personDto = new PersonDto(FIRST_NAME, LAST_NAME)
    UserDto userDto = new UserDto(ID, personDto, USERNAME, PASSWORD, ACCOUNT_ACTIVATED)

    def setup() {
        userService = new UserService(userRepository, userRoleRepository, encoder)
        mapper = Mappers.getMapper(UserMapper.class)
    }

    def "should receive userDto and save a user"() {
        given:
        Person person = new Person()
        User user = new User(ID, person, USERNAME, PASSWORD, ACCOUNT_ACTIVATED)
        UserRole userRole = new UserRole(ID, USERNAME, ROLE)

        and:
        encoder.encode(PASSWORD) >> PASSWORD
        userRepository.save(user) >> user
        userRoleRepository.save(userRole) >> userRole

        when:
        userService.save(userDto)

        then:
        1 * userRepository.save(_)
        1 * userRoleRepository.save(_)
    }

    def "should fill person with data"() {
        given:
        Person person = new Person()
        User user = new User()

        when:
        boolean filled = userService.fillPersonData(person, user, userDto)

        then:
        filled
        person.getFirstName() == FIRST_NAME
        person.getLastName() == LAST_NAME
        person.getUser() == user
    }

    def "should fill user with data"() {
        given:
        Person person = new Person()
        User user = new User()

        and:
        encoder.encode(PASSWORD) >> PASSWORD

        when:
        boolean filled = userService.fillUserData(user, person, userDto)

        then:
        filled
        user.getPerson() == person
        user.getUsername() == USERNAME
        user.getPassword() == PASSWORD
        user.isAccountActivated()
    }

    def "should fill userRole with data"() {
        given:
        UserRole userRole = new UserRole()

        when:
        boolean filled = userService.fillUserRoleData(userRole, userDto)

        then:
        filled
        userRole.getUsername() == USERNAME
        userRole.getRole() == ROLE
    }

    def "should edit user's name by id - admin version"() {
        given:
        User user = new User()
        Person person = new Person()
        person.setFirstName(FIRST_NAME)
        person.setLastName(LAST_NAME)
        user.setPerson(person)
        person.setUser(user)

        and:
        userRepository.existsById(ID) >> true
        userRepository.getById(_) >> user
        userRepository.save(user) >> user

        when:
        userService.adminEdit(userDto, personDto)

        then:
        1 * userRepository.save(user)
    }

    def "should edit user's name by username - user version"() {
        given:
        User user = new User()
        user.setUsername(USERNAME)
        Person person = new Person()
        person.setFirstName(FIRST_NAME)
        person.setLastName(LAST_NAME)
        user.setPerson(person)
        person.setUser(user)

        and:
        userRepository.getByUsername(_) >> user
        userRepository.save(user) >> user

        when:
        userService.userEdit(userDto)

        then:
        1 * userRepository.getByUsername(_)
        1 * userRepository.save(user)
    }
}
