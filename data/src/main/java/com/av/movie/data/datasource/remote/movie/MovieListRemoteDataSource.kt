package com.av.movie.data.datasource.remote.movie

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.data.api.model.Movie
import com.av.movie.data.mapper.Mapper
import javax.inject.Inject

class MovieListRemoteDataSource @Inject constructor(
    private val movieService: MovieListService,
    mapper: Mapper<MoviePreviewDTO, Movie>
): BaseRemoteDataSource<MoviePreviewDTO, Movie>(mapper),
    IMovieListRemoteDataSource<MoviePreviewDTO, Movie> {
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
}