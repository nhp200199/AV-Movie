package com.av.movie.data.datasource.remote.movie.cast

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData

interface ICastRemoteDataSource : IBaseRemoteDataSource<CastListDTO, CastListDTO> {
    suspend fun getCastsOfMovie(id: Int): ResultData<CastListDTO, String>
}