package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.MovieApi
import com.av.movie.data.repository.datasource.MovieRemoteDataSource
import com.av.movie.domain.model.OldMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRemoteDataSourceImp(private val api: MovieApi) : MovieRemoteDataSource {
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