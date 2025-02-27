package com.av.movie.domain.repository.movie

import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.movie.MoviePreviewRemoteDataSource
import javax.inject.Inject

class MoviePreviewRepository @Inject constructor(
    private val movieListDataSource: MoviePreviewRemoteDataSource
) : IMoviePreviewRepository {
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

    override suspend fun searchMovie(query: String): ResultData<List<Movie>> {
        return movieListDataSource.searchMovie(query)
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>> {
        return movieListDataSource.getRecommendationsForMovie(id)
    }
}