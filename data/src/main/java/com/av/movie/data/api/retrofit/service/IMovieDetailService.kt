package com.av.movie.data.api.retrofit.service

import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path("movie_id") id: Int): NetworkResponse<MovieDetailDTO, String>
}