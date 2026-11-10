package com.av.movie.data.repository.movie.cast

import com.av.movie.data.api.CastApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Cast
import com.av.movie.domain.repository.movie.cast.MovieCastRepository
import javax.inject.Inject

class MovieCastRepositoryImp @Inject constructor(
    private val castApi: CastApi,
    private val mapper: Mapper<CastListDTO, @JvmSuppressWildcards List<Cast>>
) : MovieCastRepository {
    override suspend fun getCastForMovie(id: Int): ResultData<List<Cast>, String> {
        return castApi.getCasts(id).map(mapper::map)
    }
}
