package com.av.movie.data.datasource.remote

import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData

interface IBaseRemoteDataPaging<T: Any, R: Any>: IBaseRemoteDataListSource<T, R> {
    suspend fun getRemoteDataPaging(
        networkCall: suspend () -> ResultData<PagingDTO<T>, String>,
    ): ResultData<List<R>, String>
}