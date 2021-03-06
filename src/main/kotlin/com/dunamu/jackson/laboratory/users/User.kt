package com.dunamu.jackson.laboratory.users

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id")
    var id: Long?,

    @Column(name = "username")
    var username: String,

    @Column(name = "github_url")
    var gitHubUrl: String,

    @Column(name = "created_at")
    var createdAt: LocalDateTime
)