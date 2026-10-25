package com.av.movie.data.repository.genre

import com.av.movie.data.datasource.remote.genre.IGenreRemoteDataSource
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Genre
import com.av.movie.domain.repository.genre.GenreRepository
import javax.inject.Inject

class GenreRepositoryImp @Inject constructor(
    private val genreRemoteDataSource: IGenreRemoteDataSource<GenreDTO, List<Genre>>
) : GenreRepository {
    override suspend fun getAllGenres(): ResultData<List<Genre>, String> {
        return genreRemoteDataSource.getAllGenres()
    }
}
