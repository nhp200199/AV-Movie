package com.av.movie.domain.repository

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getPopularMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getTopRatedMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun getUpcomingMovies(page: Int = 1): ResultData<List<Movie>, String>
    suspend fun searchMovie(query: String): ResultData<List<Movie>, String>
    suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String>
    suspend fun getMovieDetail(id: Int): ResultData<MovieDetail, String>
    suspend fun getCastForMovie(id: Int): ResultData<List<Cast>, String>
    suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String>
    suspend fun getMovieGenres(): ResultData<List<Genre>, String>
}
