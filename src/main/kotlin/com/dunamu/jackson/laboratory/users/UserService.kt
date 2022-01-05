package com.dunamu.jackson.laboratory.users

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

}