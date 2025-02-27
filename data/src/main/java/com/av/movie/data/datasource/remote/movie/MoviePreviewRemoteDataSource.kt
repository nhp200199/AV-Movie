package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.api.retrofit.service.MoviePreviewService
import com.av.movie.data.model.Movie
import com.av.movie.data.datasource.remote.BaseRemoteDataPaging
import com.av.movie.data.mapper.Mapper
import javax.inject.Inject

class MoviePreviewRemoteDataSource @Inject constructor(
    private val movieService: MoviePreviewService,
    mapper: Mapper<MoviePreviewDTO, Movie>
): BaseRemoteDataPaging<MoviePreviewDTO, Movie>(mapper),
    IMoviePreviewRemoteDataSource<MoviePreviewDTO, Movie> {
    override suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>> {
        return getRemoteDataPaging(
            networkCall = { movieService.getNowPlayingMovies(page) },
        )
    }

    override suspend fun getPopularMovies(page: Int): ResultData<List<Movie>> {
        return getRemoteDataPaging(
            networkCall = { movieService.getPopularMovies(page) },
        )
    }

    override suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>> {
        return getRemoteDataPaging(
            networkCall = { movieService.getTopRatedMovies(page) },
        )
    }

    override suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>> {
        return getRemoteDataPaging(
            networkCall = { movieService.getUpcomingMovies(page) },
        )
    }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>> {
        return getRemoteDataPaging(
            networkCall = { movieService.searchMovie(query) }
        )
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>> {
        return getRemoteDataPaging {
            movieService.getRecommendations(id)
        }
    }
}