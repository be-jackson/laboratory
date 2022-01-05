package com.dunamu.jackson.laboratory.api

import com.dunamu.jackson.laboratory.api.ApiResult.Companion.ERROR
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    private val headers: HttpHeaders = HttpHeaders().apply {
        add("Content-Type", "application/json")
    }

    private fun respond(throwable: Throwable, status: HttpStatus): ResponseEntity<ApiResult<*>> {
        return respond(throwable.message ?: "", status)
    }

    private fun respond(message: String, status: HttpStatus): ResponseEntity<ApiResult<*>> {
        return ResponseEntity(ERROR(message), headers, status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequestException(e: java.lang.Exception): ResponseEntity<*>? {
        log.debug("Bad request exception occurred: {}", e.message, e)
        return respond(e, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<*> {
        log.error("Unexpected exception occurred: {}", e.message, e)
        return respond(e, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}