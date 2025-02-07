package com.av.movie.domain.repository

import com.av.movie.domain.model.OldMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(movieId: Int): Flow<OldMovie>
    fun getLatest(): Flow<List<OldMovie>>
}