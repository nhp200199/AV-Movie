package com.av.movie.data.datasource.remote.movie.cast

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast

interface ICastRemoteDataSource : IBaseRemoteDataSource<CastListDTO, List<Cast>> {
    suspend fun getCastsOfMovie(id: Int): ResultData<List<Cast>, String>
}