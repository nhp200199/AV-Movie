package com.av.movie.domain.repository.movie.cast

import com.av.movie.data.datasource.remote.movie.cast.CastRemoteDataSource
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class MovieCastRepository @Inject constructor(
    private val castRemoteDataSource: CastRemoteDataSource
) : IMovieCastRepository {
    override suspend fun getCastForMovie(id: Int): ResultData<CastListDTO> {
        return castRemoteDataSource.getCastsOfMovie(id)
    }
}