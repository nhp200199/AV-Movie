package com.av.movie.data.repository.datasource

import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast

interface CastRemoteDataSource : IBaseRemoteDataSource<CastListDTO, List<Cast>> {
    suspend fun getCastsOfMovie(id: Int): ResultData<List<Cast>, String>
}