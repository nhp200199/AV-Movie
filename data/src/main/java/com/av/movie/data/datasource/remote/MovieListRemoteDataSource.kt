package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.api.retrofit.service.MovieListService
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.movie.MoviePreviewDTO2Movie
import com.av.movie.domain.model.Movie
import javax.inject.Inject

class MovieListRemoteDataSource @Inject constructor(
    private val movieService: MovieListService,
    private val moviePreviewDTO2Movie: MoviePreviewDTO2Movie
): IMovieListRemoteDataSource {
    override suspend fun getNowPlayingMovies(): ResultData<List<Movie>> {
        val data = movieService.getNowPlayingMovies()
        return when (data) {
            is NetworkResponse.ApiError -> ResultData.Error(Exception("Api Error"))
            NetworkResponse.NetworkError -> ResultData.Error(NoNetworkConnectionException())
            is NetworkResponse.Success -> ResultData.Success(data.body.map { moviePreviewDTO2Movie.map(it) })
            NetworkResponse.UnknownError -> ResultData.Error(UnknownException())
        }
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