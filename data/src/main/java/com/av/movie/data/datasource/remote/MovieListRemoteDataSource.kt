package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.domain.model.Movie
import javax.inject.Inject

class MovieListRemoteDataSource @Inject constructor(
    private val movieService: MovieListService
): IMovieListRemoteDataSource {
    override suspend fun getNowPlayingMovies(): NetworkResponse<List<Movie>, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(): NetworkResponse<List<Movie>, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(): NetworkResponse<List<Movie>, String> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(): NetworkResponse<List<Movie>, String> {
        TODO("Not yet implemented")
    }
}