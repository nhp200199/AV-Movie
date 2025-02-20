package com.av.movie.domain.repository.movie

import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.GenreDTO
import com.av.movie.data.api.model.ResultData

interface IGenreRepository {
    suspend fun getAllGenres(): ResultData<GenreDTO>
}