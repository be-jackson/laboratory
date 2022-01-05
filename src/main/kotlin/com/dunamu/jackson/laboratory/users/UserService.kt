package com.dunamu.jackson.laboratory.users

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = ["users"])
    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

}