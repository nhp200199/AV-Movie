package com.av.movie.data.api

import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailApi {
    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path("movie_id") id: Int): ResultData<MovieDetailDTO, String>
}