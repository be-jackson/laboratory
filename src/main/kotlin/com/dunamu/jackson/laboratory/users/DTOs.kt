package com.dunamu.jackson.laboratory.users

import java.time.LocalDateTime

data class UserDto(var username: String, var githubUrl: String?, var createdAt: LocalDateTime) {
    constructor(user: User) : this(
        user.username,
        user.gitHubUrl,
        user.createdAt
    )
}