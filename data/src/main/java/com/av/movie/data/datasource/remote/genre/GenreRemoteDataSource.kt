package com.av.movie.data.datasource.remote.genre

import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.GenreDTO
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.api.retrofit.service.GenreService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.mapper.genre.GenreDTO2Genre
import javax.inject.Inject

class GenreRemoteDataSource @Inject constructor(
    private val genreService: GenreService,
    genreDTO2Genre: GenreDTO2Genre,
) : BaseRemoteDataSource<GenreDTO, GenreDTO>(genreDTO2Genre),
    IGenreRemoteDataSource<GenreDTO, GenreDTO> {

    override suspend fun getAllGenres(): ResultData<GenreDTO> {
        return getData(
            networkCall = { genreService.getAllGenres() },
        )
    }
}