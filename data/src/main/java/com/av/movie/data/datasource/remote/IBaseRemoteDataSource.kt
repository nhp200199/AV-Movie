package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import com.av.movie.data.api.model.ResultData

interface IBaseRemoteDataSource<T: Any, R> {
    suspend fun getRemoteDataPaging(
        networkCall: suspend () -> NetworkResponse<PagingDTO<T>, String>,
    ): ResultData<List<R>>
}