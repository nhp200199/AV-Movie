package com.av.movie.domain.repository.movie

import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.datasource.remote.movie.MovieListRemoteDataSource
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val movieListDataSource: MovieListRemoteDataSource
) : IMovieListRepository {
    override suspend fun getNowPlayingMovies(): ResultData<List<Movie>> {
        return movieListDataSource.getNowPlayingMovies()
    }

    override suspend fun getPopularMovies(): ResultData<List<Movie>> {
        return movieListDataSource.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): ResultData<List<Movie>> {
        return movieListDataSource.getTopRatedMovies()
    }

    override suspend fun getUpcomingMovies(): ResultData<List<Movie>> {
        return movieListDataSource.getUpcomingMovies()
    }
}