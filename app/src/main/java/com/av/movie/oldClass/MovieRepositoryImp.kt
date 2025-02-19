package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow

class MovieRepositoryImp(
    private val movieRemoteDataSource: MovieRemoteDataSource
): MovieRepository {
    override fun getMovie(movieId: Int): Flow<OldMovie> {
        return movieRemoteDataSource.getMovie(movieId)
    }

    override fun getLatest(): Flow<List<OldMovie>> {
        return movieRemoteDataSource.getLatest()
    }
}