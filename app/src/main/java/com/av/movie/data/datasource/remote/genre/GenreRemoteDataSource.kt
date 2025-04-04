package com.av.movie.data.datasource.remote.genre

import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.retrofit.service.GenreService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.genre.GenreDTO2Genre
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