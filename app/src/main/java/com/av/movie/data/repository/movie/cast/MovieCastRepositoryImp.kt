package com.av.movie.data.repository.movie.cast

import com.av.movie.data.datasource.remote.movie.cast.ICastRemoteDataSource
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast
import com.av.movie.domain.repository.movie.cast.MovieCastRepository
import javax.inject.Inject

class MovieCastRepositoryImp @Inject constructor(
    private val castRemoteDataSource: ICastRemoteDataSource
) : MovieCastRepository {
    override suspend fun getCastForMovie(id: Int): ResultData<List<Cast>, String> {
        return castRemoteDataSource.getCastsOfMovie(id)
    }
}
