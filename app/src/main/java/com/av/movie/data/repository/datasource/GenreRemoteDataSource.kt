package com.av.movie.data.repository.datasource

import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Genre

interface GenreRemoteDataSource : IBaseRemoteDataSource<GenreDTO, List<Genre>> {
    suspend fun getAllGenres(): ResultData<List<Genre>, String>
}