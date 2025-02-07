package com.av.movie.data.repository.datasource

import com.av.movie.domain.model.OldMovie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovie(movieId: Int): Flow<OldMovie>
    fun getLatest(): Flow<List<OldMovie>>
}