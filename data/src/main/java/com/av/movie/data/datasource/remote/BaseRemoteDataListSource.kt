package com.av.movie.data.datasource.remote

import com.av.movie.data.R
import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.Mapper

open class BaseRemoteDataListSource<T : Any, R>(
    mapper: Mapper<T, R>
) : IBaseRemoteDataListSource<T, R>,
    BaseRemoteDataSource<T, R> (mapper){
    override suspend fun getDataList(
        networkCall: suspend () -> NetworkResponse<List<T>, String>
    ): ResultData<List<R>> {
        val data = networkCall()
        return when (data) {
            is NetworkResponse.ApiError -> ResultData.Error(Exception("Api Error"))
            NetworkResponse.NetworkError -> ResultData.Error(NoNetworkConnectionException())
            is NetworkResponse.Success -> ResultData.Success(data.body.map { mapper.map(it) })
            NetworkResponse.UnknownError -> ResultData.Error(UnknownException())
        }
    }
}