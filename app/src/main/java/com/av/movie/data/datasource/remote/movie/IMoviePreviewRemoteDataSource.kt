package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.IBaseRemoteDataSource

interface IMoviePreviewRemoteDataSource<T: Any, R: Any>: IBaseRemoteDataSource<T, R> {
    suspend fun getNowPlayingMovies(page: Int): ResultData<List<R>, String>
    suspend fun getPopularMovies(page: Int): ResultData<List<R>, String>
    suspend fun getTopRatedMovies(page: Int): ResultData<List<R>, String>
    suspend fun getUpcomingMovies(page: Int): ResultData<List<R>, String>
    suspend fun searchMovie(query: String): ResultData<List<R>, String>
    suspend fun getRecommendationsForMovie(id: Int): ResultData<List<R>, String>
}