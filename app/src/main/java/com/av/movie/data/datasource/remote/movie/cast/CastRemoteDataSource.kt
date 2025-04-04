package com.av.movie.data.datasource.remote.movie.cast

import com.av.movie.data.retrofit.service.ICastService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.Mapper
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class CastRemoteDataSource @Inject constructor(
    private val castService: ICastService,
    mapper: Mapper<CastListDTO, CastListDTO>
) : ICastRemoteDataSource,
    BaseRemoteDataSource<CastListDTO, CastListDTO>(mapper) {

    override suspend fun getCastsOfMovie(id: Int): ResultData<CastListDTO> {
        return getData { castService.getCasts(id) }
    }
}