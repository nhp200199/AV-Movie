package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.domain.model.Movie
import javax.inject.Inject

class MovieListRemoteDataSource @Inject constructor(
    private val movieService: MovieListService,
    mapper: MoviePreviewDTO2Movie
): BaseRemoteDataSource<MoviePreviewDTO, Movie>(mapper),
    IMovieListRemoteDataSource<MoviePreviewDTO, Movie> {
    override suspend fun getNowPlayingMovies(): ResultData<List<Movie>> {
        return getRemoteData(
            networkCall = { movieService.getNowPlayingMovies() },
        )
    }

    override suspend fun getPopularMovies(): ResultData<List<Movie>> {
        return getRemoteData(
            networkCall = { movieService.getPopularMovies() },
        )
    }

    override suspend fun getTopRatedMovies(): ResultData<List<Movie>> {
        return getRemoteData(
            networkCall = { movieService.getTopRatedMovies() },
        )
    }

    override suspend fun getUpcomingMovies(): ResultData<List<Movie>> {
        return getRemoteData(
            networkCall = { movieService.getUpcomingMovies() },
        )
    }
}