package com.av.movie.data.datasource.remote

import com.av.movie.data.model.ResultData

interface IBaseRemoteDataListSource<T: Any, R: Any>: IBaseRemoteDataSource<T, R> {
    suspend fun getDataList(
        networkCall: suspend () -> ResultData<List<T>, String>,
    ): ResultData<List<R>, String>
}