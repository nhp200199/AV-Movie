package com.av.movie.data.datasource.remote.movie.cast

import com.av.movie.data.Mapper
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.retrofit.service.ICastService
import com.av.movie.domain.model.Cast
import javax.inject.Inject

class CastRemoteDataSource @Inject constructor(
    private val castService: ICastService,
    mapper: Mapper<CastListDTO, List<Cast>>
) : ICastRemoteDataSource,
    BaseRemoteDataSource<CastListDTO, List<Cast>>(mapper) {

    override suspend fun getCastsOfMovie(id: Int): ResultData<List<Cast>, String> {
        return getData { castService.getCasts(id) }
    }
}