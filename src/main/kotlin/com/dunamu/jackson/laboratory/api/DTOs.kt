package com.dunamu.jackson.laboratory.api

data class ApiResult<T>(
    val succeed: Boolean,
    val data: T?,
    val error: String?
) {
    companion object {
        fun <T> OK(data: T): ApiResult<T> = ApiResult(true, data, null)
        fun ERROR(message: String): ApiResult<Any?> = ApiResult(false, null, message)
    }
}