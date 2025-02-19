package com.av.movie.oldClass

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("/movie/{movieId}")
    suspend fun getMovie(
        @Path("movieId") movieId: Int
    ): Response<OldMovie>

    @GET("movie/latest")
    suspend fun getLatest(): Response<List<OldMovie>>
}