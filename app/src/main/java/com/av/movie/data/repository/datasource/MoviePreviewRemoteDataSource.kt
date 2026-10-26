package com.av.movie.data.repository.datasource

import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Movie

interface MoviePreviewRemoteDataSource : IBaseRemoteDataPaging<MoviePreviewDTO, Movie> {
    suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>, String>
    suspend fun getPopularMovies(page: Int): ResultData<List<Movie>, String>
    suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>, String>
    suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>, String>
    suspend fun searchMovie(query: String): ResultData<List<Movie>, String>
    suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String>
}