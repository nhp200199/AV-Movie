package com.av.movie.data.repository.datasource

import com.av.movie.data.model.ResultData

interface IBaseRemoteDataSource<T : Any, R : Any> {
    suspend fun getData(
        networkCall: suspend () -> ResultData<T, String>
    ): ResultData<R, String>
}