package com.av.movie.domain.repository.movie.cast

import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData

interface IMovieCastRepository {
    suspend fun getCastForMovie(id: Int): ResultData<CastListDTO, String>
}