package com.av.movie.domain.repository.movie

import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.GenreDTO
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.datasource.remote.genre.IGenreRemoteDataSource
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val genreRemoteDataSource: IGenreRemoteDataSource<GenreDTO, GenreDTO>
) : IGenreRepository {
    override suspend fun getAllGenres(): ResultData<GenreDTO> {
        return genreRemoteDataSource.getAllGenres()
    }
}