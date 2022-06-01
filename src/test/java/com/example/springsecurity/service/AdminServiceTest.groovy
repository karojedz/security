package com.example.springsecurity.service

import com.example.springsecurity.model.UserDto
import com.example.springsecurity.model.UserForm
import spock.lang.Specification

class AdminServiceTest extends Specification {

    private AdminService adminService

    UserService userService = Mock()

    final Long ID = 1L
    final String FIRST_NAME = "firstName"
    final String LAST_NAME = "lastName"
    final String USERNAME = "username"
    final String PASSWORD = "password"
    final boolean ENABLED = true

    UserForm userForm = new UserForm(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD)

    def setup() {
        adminService = new AdminService(userService)
    }

    def "should invoke an adminEdit method from UserService"() {
        given:
        UserDto expected = new UserDto(FIRST_NAME, LAST_NAME, USERNAME, ENABLED)

        and:
        userService.adminEdit(userForm) >> expected

        expect:
        expected == adminService.editUser(userForm)
    }
}
