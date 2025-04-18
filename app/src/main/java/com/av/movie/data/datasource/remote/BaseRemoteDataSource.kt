package com.av.movie.data.datasource.remote

import com.av.movie.data.model.ResultData
import com.av.movie.data.Mapper

open class BaseRemoteDataSource<T: Any, R: Any>(
    open val mapper: Mapper<T, R>
) : IBaseRemoteDataSource<T, R> {
    override suspend fun getData(
        networkCall: suspend () -> ResultData<T, String>
    ): ResultData<R, String> {
        val result = networkCall()
        return when (result) {
            is ResultData.ApiError -> ResultData.ApiError(result.body)
            is ResultData.OperationError -> ResultData.OperationError(result.exception)
            is ResultData.Success -> ResultData.Success(mapper.map(result.data))
        }
    }
}