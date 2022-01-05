package com.dunamu.jackson.laboratory.api

import com.dunamu.jackson.laboratory.api.ApiResult.Companion.OK
import com.dunamu.jackson.laboratory.users.User
import com.dunamu.jackson.laboratory.users.UserDto
import com.dunamu.jackson.laboratory.users.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class APIs(private val userService: UserService) {

    @GetMapping("/user/{username}")
    fun findUser(@PathVariable username: String): ApiResult<UserDto> {
        val user: User = userService.findByUsername(username)
            ?: throw IllegalArgumentException("Could not found user for $username")
        return OK(UserDto(user))
    }

}