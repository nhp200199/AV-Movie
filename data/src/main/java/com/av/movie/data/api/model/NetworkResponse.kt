package com.av.movie.data.api.model

import java.io.IOException

sealed class NetworkResponse<out T : Any, out E: Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<E: Any>(val body: E, val code: Int) : NetworkResponse<Nothing, E>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    object UnknownError : NetworkResponse<Nothing, Nothing>()

    override fun toString(): String {
        return when(this) {
            is ApiError -> "ApiError[code=$code]"
            is NetworkError -> "NetworkError[exception=$error]"
            is Success -> "Success[body=$body]"
            UnknownError -> "UnknownError"
        }
    }
}
