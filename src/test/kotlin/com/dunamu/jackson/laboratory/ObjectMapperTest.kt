package com.dunamu.jackson.laboratory

import com.dunamu.jackson.laboratory.users.User
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import java.time.LocalDateTime.now

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ObjectMapperTest {

    private val log = LoggerFactory.getLogger(javaClass)

    lateinit var objectMapper: ObjectMapper

    @BeforeAll
    fun beforeAll() {
        objectMapper = jacksonObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

            activateDefaultTyping(polymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
            registerModule(JavaTimeModule())
        }
    }

    @Test
    fun test() {
        val user = User(1L, "jackson", null, now())
        val json = objectMapper.writeValueAsString(user)
        log.info("Json: $json")
    }

}