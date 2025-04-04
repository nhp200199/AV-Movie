package com.av.movie.domain.repository.movie

import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData


interface IMoviePreviewRepository {
    suspend fun getNowPlayingMovies(page: Int = 1): ResultData<List<Movie>>
    suspend fun getPopularMovies(page: Int = 1): ResultData<List<Movie>>
    suspend fun getTopRatedMovies(page: Int = 1): ResultData<List<Movie>>
    suspend fun getUpcomingMovies(page: Int = 1): ResultData<List<Movie>>
    suspend fun searchMovie(query: String): ResultData<List<Movie>>
    suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>>
    fun getMoviePreview(id: Int): Movie?
}