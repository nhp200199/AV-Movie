package com.av.movie.data.datasource.remote

import com.av.movie.data.model.ResultData
import com.av.movie.data.Mapper

open class BaseRemoteDataListSource<T : Any, R: Any>(
    mapper: Mapper<T, R>
) : IBaseRemoteDataListSource<T, R>,
    BaseRemoteDataSource<T, R> (mapper){
    override suspend fun getDataList(
        networkCall: suspend () -> ResultData<List<T>, String>
    ): ResultData<List<R>, String> {
        val result = networkCall()
        return when (result) {
            is ResultData.ApiError -> ResultData.ApiError(result.body)
            is ResultData.OperationError -> ResultData.OperationError(result.exception)
            is ResultData.Success -> ResultData.Success(result.data.map { mapper.map(it) })
        }
    }
}