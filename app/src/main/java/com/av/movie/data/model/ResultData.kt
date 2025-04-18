package com.av.movie.data.model

sealed class ResultData<out T: Any, out E: Any> {
    data class Success<out T: Any>(val data: T) : ResultData<T, Nothing>()
    data class OperationError(val exception: Exception) : ResultData<Nothing, Nothing>()
    data class ApiError<out E: Any>(val body: E): ResultData<Nothing, E>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is OperationError -> "Error[exception=$exception]"
            is ApiError<*> -> "ApiError"
        }
    }
}