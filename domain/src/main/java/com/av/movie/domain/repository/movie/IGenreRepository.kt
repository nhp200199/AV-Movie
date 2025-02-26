package com.av.movie.domain.repository.movie

import com.av.movie.data.model.Genre
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData

interface IGenreRepository {
    suspend fun getAllGenres(): ResultData<GenreDTO>
}