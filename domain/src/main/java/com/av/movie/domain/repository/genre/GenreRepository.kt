package com.av.movie.domain.repository.genre

import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.datasource.remote.genre.IGenreRemoteDataSource
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val genreRemoteDataSource: IGenreRemoteDataSource<GenreDTO, GenreDTO>
) : IGenreRepository {
    override suspend fun getAllGenres(): ResultData<GenreDTO> {
        return genreRemoteDataSource.getAllGenres()
    }
}