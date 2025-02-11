package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData
import com.av.movie.domain.model.Movie

interface IMovieListRemoteDataSource {
    suspend fun getNowPlayingMovies(): ResultData<List<Movie>>
    suspend fun getPopularMovies(): NetworkResponse<List<Movie>, String>
    suspend fun getTopRatedMovies(): NetworkResponse<List<Movie>, String>
    suspend fun getUpcomingMovies(): NetworkResponse<List<Movie>, String>
}