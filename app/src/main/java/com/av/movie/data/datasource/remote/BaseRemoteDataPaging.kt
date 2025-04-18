package com.av.movie.data.datasource.remote

import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRemoteDataPaging<T : Any, R: Any>(
    mapper: Mapper<T, R>
) : IBaseRemoteDataPaging<T, R>,
    BaseRemoteDataListSource<T, R>(mapper) {
        override suspend fun getRemoteDataPaging(
            networkCall: suspend () -> ResultData<PagingDTO<T>, String>,
    ): ResultData<List<R>, String> {
        val result = withContext(Dispatchers.IO) {
            networkCall()
        }
        return when (result) {
            is ResultData.ApiError -> ResultData.ApiError(result.body)
            is ResultData.OperationError -> ResultData.OperationError(result.exception)
            is ResultData.Success -> ResultData.Success(result.data.results.map { mapper.map(it) })
        }
    }
}