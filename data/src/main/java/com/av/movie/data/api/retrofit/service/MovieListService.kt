package com.av.movie.data.api.retrofit.service

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.NetworkResponse
import retrofit2.http.GET

interface MovieListService {
    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(): NetworkResponse<List<MoviePreviewDTO>, String>
}