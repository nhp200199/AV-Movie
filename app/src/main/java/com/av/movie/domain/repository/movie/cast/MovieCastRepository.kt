package com.av.movie.domain.repository.movie.cast

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast

interface MovieCastRepository {
    suspend fun getCastForMovie(id: Int): ResultData<List<Cast>, String>
}