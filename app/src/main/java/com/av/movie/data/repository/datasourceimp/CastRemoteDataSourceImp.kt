package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.CastApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.CastRemoteDataSource
import com.av.movie.domain.model.Cast
import javax.inject.Inject

class CastRemoteDataSourceImp @Inject constructor(
    private val castApi: CastApi,
    mapper: Mapper<CastListDTO, List<Cast>>
) : CastRemoteDataSource,
    BaseRemoteDataSource<CastListDTO, List<Cast>>(mapper) {

    override suspend fun getCastsOfMovie(id: Int): ResultData<List<Cast>, String> {
        return getData { castApi.getCasts(id) }
    }
}