package com.av.movie.data.datasource.remote.genre

import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.datasource.remote.IBaseRemoteDataSource

interface IGenreRemoteDataSource<T: Any, R> : IBaseRemoteDataSource<T, R> {
    suspend fun getAllGenres(): ResultData<R>
}