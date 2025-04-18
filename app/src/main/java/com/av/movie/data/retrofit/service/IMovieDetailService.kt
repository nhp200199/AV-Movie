package com.av.movie.data.retrofit.service

import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface IMovieDetailService {
    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path("movie_id") id: Int): ResultData<MovieDetailDTO, String>
}