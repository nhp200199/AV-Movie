package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovie(movieId: Int): Flow<com.av.movie.oldClass.OldMovie>
    fun getLatest(): Flow<List<com.av.movie.oldClass.OldMovie>>
}