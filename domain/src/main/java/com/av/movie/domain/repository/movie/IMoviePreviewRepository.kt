package com.av.movie.domain.repository.movie

import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.ResultData


interface IMoviePreviewRepository {
    suspend fun getNowPlayingMovies(): ResultData<List<Movie>>
    suspend fun getPopularMovies(): ResultData<List<Movie>>
    suspend fun getTopRatedMovies(): ResultData<List<Movie>>
    suspend fun getUpcomingMovies(): ResultData<List<Movie>>
}