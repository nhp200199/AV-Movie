package com.av.movie.data.datasource.remote.genre

import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.IBaseRemoteDataSource

interface IGenreRemoteDataSource<T: Any, R: Any> : IBaseRemoteDataSource<T, R> {
    suspend fun getAllGenres(): ResultData<R, String>
}