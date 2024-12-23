package com.av.movie.data.repository.datasource

import com.av.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovie(movieId: Int): Flow<Movie>
    fun getLatest(): Flow<List<Movie>>
}