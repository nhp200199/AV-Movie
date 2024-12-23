package com.av.movie.data.api

import com.av.movie.domain.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("/movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId: Int
    ): Response<Movie>

    @GET("movie/latest")
    suspend fun getLatest(): Response<List<Movie>>
}