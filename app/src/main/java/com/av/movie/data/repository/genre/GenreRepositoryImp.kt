package com.av.movie.data.repository.genre

import com.av.movie.data.api.GenreApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Genre
import com.av.movie.domain.repository.genre.GenreRepository
import javax.inject.Inject

class GenreRepositoryImp @Inject constructor(
    private val genreApi: GenreApi,
    private val mapper: Mapper<GenreDTO, @JvmSuppressWildcards List<Genre>>
) : GenreRepository {
    override suspend fun getAllGenres(): ResultData<List<Genre>, String> {
        return genreApi.getAllGenres().map(mapper::map)
    }
}
