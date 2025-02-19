package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRemoteDataSourceImp(private val api: com.av.movie.oldClass.MovieApi) :
    com.av.movie.oldClass.MovieRemoteDataSource {
    override fun getMovie(movieId: Int): Flow<OldMovie> {
        return flow {
            api.getMovie(movieId)
        }
    }

    override fun getLatest(): Flow<List<OldMovie>> {
        return flow {
            api.getLatest()
        }
    }
}