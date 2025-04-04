package com.av.movie.data.datasource.remote

import com.av.movie.data.model.NetworkResponse
import com.av.movie.data.model.ResultData

interface IBaseRemoteDataSource<T: Any, R> {
    suspend fun getData(
        networkCall: suspend () -> NetworkResponse<T, String>
    ): ResultData<R>
}