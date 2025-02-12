package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData

interface IBaseRemoteDataSource<T, R> {
    suspend fun getRemoteData(
        networkCall: suspend () -> NetworkResponse<List<T>, String>,
    ): ResultData<List<R>>
}