package com.av.movie.data.datasource.remote

import com.av.movie.data.R
import com.av.movie.data.model.NetworkResponse
import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRemoteDataPaging<T : Any, R>(
    mapper: Mapper<T, R>
) : IBaseRemoteDataPaging<T, R>,
    BaseRemoteDataListSource<T, R>(mapper) {
    override suspend fun getRemoteDataPaging(
        networkCall: suspend () -> NetworkResponse<PagingDTO<T>, String>,
    ): ResultData<List<R>> {
        val data = withContext(Dispatchers.IO) {
            networkCall()
        }
        return when (data) {
            is NetworkResponse.ApiError -> ResultData.Error(Exception("Api Error"))
            NetworkResponse.NetworkError -> ResultData.Error(NoNetworkConnectionException())
            is NetworkResponse.Success -> ResultData.Success(data.body.results.map { mapper.map(it) })
            NetworkResponse.UnknownError -> ResultData.Error(UnknownException())
        }
    }
}