package com.example.springsecurity.service

import com.example.springsecurity.model.PersonDto
import com.example.springsecurity.model.UserDto
import spock.lang.Specification

class AdminServiceTest extends Specification {

    private AdminService adminService

    UserService userService = Mock()

    final Long ID = 1L
    final String FIRST_NAME = "firstName"
    final String LAST_NAME = "lastName"
    final String USERNAME = "username"
    final String PASSWORD = "password"
    final boolean ACCOUNT_ACTIVATED = true

    PersonDto personDto = new PersonDto(FIRST_NAME, LAST_NAME)
    UserDto userDto = new UserDto(ID, personDto, USERNAME, PASSWORD, ACCOUNT_ACTIVATED)

    def setup() {
        adminService = new AdminService(userService)
    }

    def "should invoke an adminEdit method from UserService"() {
        given:
        userService.adminEdit(userDto, personDto) >> userDto

        when:
        adminService.editUser(userDto, personDto)

        then:
        1 * userService.adminEdit(userDto, personDto)
    }
}
