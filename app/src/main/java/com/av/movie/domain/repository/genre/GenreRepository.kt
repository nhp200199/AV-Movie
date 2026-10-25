package com.av.movie.domain.repository.genre

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Genre

interface GenreRepository {
    suspend fun getAllGenres(): ResultData<List<Genre>, String>
}