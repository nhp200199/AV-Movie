package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData

interface IBaseRemoteDataListSource<T: Any, R>: IBaseRemoteDataSource<T, R> {
    suspend fun getDataList(
        networkCall: suspend () -> NetworkResponse<List<T>, String>,
    ): ResultData<List<R>>
}