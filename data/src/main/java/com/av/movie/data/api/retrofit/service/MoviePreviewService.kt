package com.av.movie.data.api.retrofit.service

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviePreviewService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
}