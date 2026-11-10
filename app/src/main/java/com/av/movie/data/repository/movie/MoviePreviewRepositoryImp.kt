package com.av.movie.data.repository.movie

import com.av.movie.data.api.MoviePreviewApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Movie
import com.av.movie.domain.repository.movie.MoviePreviewRepository
import javax.inject.Inject

class MoviePreviewRepositoryImp @Inject constructor(
    private val moviePreviewApi: MoviePreviewApi,
    private val mapper: Mapper<MoviePreviewDTO, Movie>
) : MoviePreviewRepository {
    private val moviePreviews: MutableSet<Movie> = mutableSetOf()

    override suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.getNowPlayingMovies(page).map { paging ->
            paging.results.map(mapper::map)
        }
        handleMoviesResult(result)
        return result
    }

    override suspend fun getPopularMovies(page: Int): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.getPopularMovies(page).map { paging ->
            paging.results.map(mapper::map)
        }
        handleMoviesResult(result)
        return result
    }

    override suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.getTopRatedMovies(page).map { paging ->
            paging.results.map(mapper::map)
        }
        handleMoviesResult(result)
        return result
    }

    override suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.getUpcomingMovies(page).map { paging ->
            paging.results.map(mapper::map)
        }
        handleMoviesResult(result)
        return result
    }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.searchMovie(query).map { paging ->
            paging.results.map(mapper::map)
        }
        handleMoviesResult(result)
        return result
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String> {
        val result = moviePreviewApi.getRecommendations(id).map { paging ->
            paging.results.map(mapper::map)
        }
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
