package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import com.av.movie.data.api.model.ResultData

interface IBaseRemoteDataSource<T: Any, R> {
    suspend fun getData(
        networkCall: suspend () -> NetworkResponse<T, String>
    ): ResultData<R>
}