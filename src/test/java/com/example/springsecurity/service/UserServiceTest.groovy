package com.example.springsecurity.service

import com.example.springsecurity.mapper.UserMapper
import com.example.springsecurity.model.Person
import com.example.springsecurity.model.User
import com.example.springsecurity.model.UserForm
import com.example.springsecurity.model.UserRole
import com.example.springsecurity.repository.UserRepository
import com.example.springsecurity.repository.UserRoleRepository
import org.mapstruct.factory.Mappers
import org.springframework.security.core.context.SecurityContextHolder
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
    final boolean ENABLED = false
    final String ROLE = "ROLE_USER"

    UserForm userForm = new UserForm(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD)

    def setup() {
        userService = new UserService(userRepository, userRoleRepository, encoder)
        mapper = Mappers.getMapper(UserMapper.class)
    }

    def "should receive userForm and save a user"() {
        given:
        Person person = new Person()
        User user = new User(ID, person, USERNAME, PASSWORD, ENABLED)
        UserRole userRole = new UserRole(ID, USERNAME, ROLE)

        and:
        encoder.encode(_) >> PASSWORD
        userRepository.save(user) >> user
        userRoleRepository.save(userRole) >> userRole

        when:
        userService.save(userForm)

        then:
        1 * userRepository.save(_)
        1 * userRoleRepository.save(_)
    }

    def "should edit user's name by id - admin version"() {
        given:
        User user = new User()
        Person person = new Person()
        person.setFirstName(FIRST_NAME)
        person.setLastName(LAST_NAME)
        user.setPerson(person)

        and:
        userRepository.existsById(ID) >> true
        userRepository.getById(_) >> user
        userRepository.save(user) >> user

        when:
        userService.adminEdit(userForm)

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

        and:
        SecurityContextHolder.getContext().getAuthentication().getName() >> USERNAME //rzuca NullPointerException
        userRepository.getByUsername(_) >> user
        userRepository.save(user) >> user

        when:
        userService.userEdit(userForm)

        then:
        1 * userRepository.getByUsername(_)
        1 * userRepository.save(user)
    }
}
