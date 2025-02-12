package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.api.model.ResultData
import com.av.movie.data.datasource.remote.IBaseRemoteDataSource

interface IMovieListRemoteDataSource<T: Any, R>: IBaseRemoteDataSource<T, R> {
    suspend fun getNowPlayingMovies(): ResultData<List<R>>
    suspend fun getPopularMovies(): ResultData<List<R>>
    suspend fun getTopRatedMovies(): ResultData<List<R>>
    suspend fun getUpcomingMovies(): ResultData<List<R>>
}