package com.av.movie.data.datasource.remote

import com.av.movie.data.R
import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import com.av.movie.data.api.model.ResultData

interface IBaseRemoteDataPaging<T: Any, R>: IBaseRemoteDataListSource<T, R> {
    suspend fun getRemoteDataPaging(
        networkCall: suspend () -> NetworkResponse<PagingDTO<T>, String>,
    ): ResultData<List<R>>
}