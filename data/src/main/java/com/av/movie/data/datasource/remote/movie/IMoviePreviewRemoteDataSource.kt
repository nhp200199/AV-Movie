package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.IBaseRemoteDataSource

interface IMoviePreviewRemoteDataSource<T: Any, R>: IBaseRemoteDataSource<T, R> {
    suspend fun getNowPlayingMovies(page: Int = 1): ResultData<List<R>>
    suspend fun getPopularMovies(page: Int = 1): ResultData<List<R>>
    suspend fun getTopRatedMovies(page: Int = 1): ResultData<List<R>>
    suspend fun getUpcomingMovies(page: Int = 1): ResultData<List<R>>
    suspend fun searchMovie(query: String): ResultData<List<R>>
    suspend fun getRecommendations(id: Int): ResultData<List<R>>
}