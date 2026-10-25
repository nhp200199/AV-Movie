package com.av.movie.data.repository.movie

import com.av.movie.data.datasource.remote.movie.IMoviePreviewRemoteDataSource
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Movie
import com.av.movie.domain.repository.movie.MoviePreviewRepository
import javax.inject.Inject

class MoviePreviewRepositoryImp @Inject constructor(
    private val movieListDataSource: IMoviePreviewRemoteDataSource<MoviePreviewDTO, Movie>
) : MoviePreviewRepository {
    private val moviePreviews: MutableSet<Movie> = mutableSetOf()

    override suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>, String> {
        val result = movieListDataSource.getNowPlayingMovies(page = page)
        handleMoviesResult(result)
        return result
    }

    override suspend fun getPopularMovies(page: Int): ResultData<List<Movie>, String> {
        val result = movieListDataSource.getPopularMovies(page = page)
        handleMoviesResult(result)
        return result
    }

    override suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>, String> {
        val result = movieListDataSource.getTopRatedMovies(page = page)
        handleMoviesResult(result)
        return result
    }

    override suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>, String> {
        val result = movieListDataSource.getUpcomingMovies(page = page)
        handleMoviesResult(result)
        return result
    }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>, String> {
        val result = movieListDataSource.searchMovie(query)
        handleMoviesResult(result)
        return result
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String> {
        val result = movieListDataSource.getRecommendationsForMovie(id)
        handleMoviesResult(result)
        return result
    }

    override fun getMoviePreview(id: Int): Movie? = moviePreviews.firstOrNull { it.id == id }

    private fun handleMoviesResult(movieResult: ResultData<List<Movie>, String>) {
        if (movieResult is ResultData.Success) {
            moviePreviews.addAll(movieResult.data)
        }
    }
}
