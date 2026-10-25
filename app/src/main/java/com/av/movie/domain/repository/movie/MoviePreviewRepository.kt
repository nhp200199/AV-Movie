package com.av.movie.domain.repository.movie

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Movie

interface MoviePreviewRepository {
    suspend fun getNowPlayingMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getPopularMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getTopRatedMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getUpcomingMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun searchMovie(query: String): ResultData<List<Movie>, String>
    suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String>
    fun getMoviePreview(id: Int): Movie?
}