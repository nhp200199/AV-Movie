package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovie(movieId: Int): Flow<OldMovie>
    fun getLatest(): Flow<List<OldMovie>>
}