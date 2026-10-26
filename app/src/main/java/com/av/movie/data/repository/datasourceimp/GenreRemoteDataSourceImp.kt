package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.GenreApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.GenreRemoteDataSource
import com.av.movie.domain.model.Genre
import javax.inject.Inject

class GenreRemoteDataSourceImp @Inject constructor(
    private val genreApi: GenreApi,
    genreDTO2Genre: Mapper<GenreDTO, @JvmSuppressWildcards List<Genre>>
) : BaseRemoteDataSource<GenreDTO, List<Genre>>(genreDTO2Genre),
    GenreRemoteDataSource {

    override suspend fun getAllGenres(): ResultData<List<Genre>, String> {
        return getData(
            networkCall = { genreApi.getAllGenres() },
        )
    }
}