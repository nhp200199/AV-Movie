package com.av.movie.domain.repository.movie

import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.movie.MoviePreviewRemoteDataSource
import javax.inject.Inject

class MoviePreviewRepository @Inject constructor(
    private val movieListDataSource: MoviePreviewRemoteDataSource
) : IMoviePreviewRepository {
    private val moviePreviews: MutableSet<Movie> = mutableSetOf()

    override suspend fun getNowPlayingMovies(): ResultData<List<Movie>> {
        val result = movieListDataSource.getNowPlayingMovies()
        handleMoviesResult(result)
        return result
    }

    override suspend fun getPopularMovies(): ResultData<List<Movie>> {
        val result = movieListDataSource.getPopularMovies()
        handleMoviesResult(result)
        return result
    }

    override suspend fun getTopRatedMovies(): ResultData<List<Movie>> {
        val result = movieListDataSource.getTopRatedMovies()
        handleMoviesResult(result)
        return result
    }

    override suspend fun getUpcomingMovies(): ResultData<List<Movie>> {
        val result = movieListDataSource.getUpcomingMovies()
        handleMoviesResult(result)
        return result
    }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>> {
        val result = movieListDataSource.searchMovie(query)
        handleMoviesResult(result)
        return result
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>> {
        val result = movieListDataSource.getRecommendationsForMovie(id)
        handleMoviesResult(result)
        return result
    }

    override fun getMoviePreview(id: Int): Movie? = moviePreviews.firstOrNull { it.id == id }

    private fun handleMoviesResult(movieResult: ResultData<List<Movie>>) {
        if (movieResult is ResultData.Success) {
            moviePreviews.addAll(movieResult.data)
        }
    }
}