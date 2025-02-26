package com.av.movie.data.datasource.remote

import com.av.movie.data.model.NetworkResponse
import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.Mapper

open class BaseRemoteDataSource<T: Any, R>(
    open val mapper: Mapper<T, R>
) : IBaseRemoteDataSource<T, R> {
    override suspend fun getData(
        networkCall: suspend () -> NetworkResponse<T, String>
    ): ResultData<R> {
        val data = networkCall()
        return when (data) {
            is NetworkResponse.ApiError -> ResultData.Error(Exception("Api Error"))
            NetworkResponse.NetworkError -> ResultData.Error(NoNetworkConnectionException())
            is NetworkResponse.Success -> ResultData.Success(mapper.map(data.body))
            NetworkResponse.UnknownError -> ResultData.Error(UnknownException())
        }
    }
}