package com.av.movie.data.datasource.remote.genre

import com.av.movie.data.Mapper
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.retrofit.service.GenreService
import com.av.movie.domain.model.Genre
import javax.inject.Inject

class GenreRemoteDataSource @Inject constructor(
    private val genreService: GenreService,
    genreDTO2Genre: Mapper<GenreDTO, List<Genre>>,
) : BaseRemoteDataSource<GenreDTO, List<Genre>>(genreDTO2Genre),
    IGenreRemoteDataSource<GenreDTO, List<Genre>> {

    override suspend fun getAllGenres(): ResultData<List<Genre>, String> {
        return getData(
            networkCall = { genreService.getAllGenres() },
        )
    }
}