package com.av.movie.data.api

import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET

interface GenreApi {
    @GET("genre/movie/list")
    suspend fun getAllGenres(): ResultData<GenreDTO, String>
}