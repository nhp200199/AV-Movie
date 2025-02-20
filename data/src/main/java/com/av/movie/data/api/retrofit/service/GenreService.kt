package com.av.movie.data.api.retrofit.service

import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.GenreDTO
import com.av.movie.data.api.model.NetworkResponse
import retrofit2.http.GET

interface GenreService {
    @GET("genre/movie/list")
    suspend fun getAllGenres(): NetworkResponse<GenreDTO, String>
}