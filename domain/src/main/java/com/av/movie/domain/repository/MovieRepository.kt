package com.av.movie.domain.repository

import com.av.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(movieId: Int): Flow<Movie>
    fun getLatest(): Flow<List<Movie>>
}