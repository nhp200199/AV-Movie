package com.av.movie.data.repository

import com.av.movie.data.repository.datasource.MovieRemoteDataSource
import com.av.movie.domain.model.Movie
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImp(
    private val movieRemoteDataSource: MovieRemoteDataSource
): MovieRepository {
    override fun getMovie(movieId: Int): Flow<Movie> {
        return movieRemoteDataSource.getMovie(movieId)
    }

    override fun getLatest(): Flow<List<Movie>> {
        return movieRemoteDataSource.getLatest()
    }
}