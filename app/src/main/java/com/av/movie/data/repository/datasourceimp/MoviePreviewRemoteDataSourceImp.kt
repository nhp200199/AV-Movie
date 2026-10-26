package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.MoviePreviewApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.MoviePreviewRemoteDataSource
import com.av.movie.domain.model.Movie
import javax.inject.Inject

class MoviePreviewRemoteDataSourceImp @Inject constructor(
    private val moviePreviewApi: MoviePreviewApi,
    mapper: Mapper<MoviePreviewDTO, Movie>
) : BaseRemoteDataPaging<MoviePreviewDTO, Movie>(mapper),
    MoviePreviewRemoteDataSource {

    override suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>, String> {
        return getRemoteDataPaging(
            networkCall = { moviePreviewApi.getNowPlayingMovies(page) },
        )
    }

    override suspend fun getPopularMovies(page: Int): ResultData<List<Movie>, String> {
        return getRemoteDataPaging(
            networkCall = { moviePreviewApi.getPopularMovies(page) },
        )
    }

    override suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>, String> {
        return getRemoteDataPaging(
            networkCall = { moviePreviewApi.getTopRatedMovies(page) },
        )
    }

    override suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>, String> {
        return getRemoteDataPaging(
            networkCall = { moviePreviewApi.getUpcomingMovies(page) },
        )
    }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>, String> {
        return getRemoteDataPaging(
            networkCall = { moviePreviewApi.searchMovie(query) }
        )
    }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String> {
        return getRemoteDataPaging {
            moviePreviewApi.getRecommendations(id)
        }
    }
}