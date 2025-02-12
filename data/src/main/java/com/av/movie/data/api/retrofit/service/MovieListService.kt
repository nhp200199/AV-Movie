package com.av.movie.data.api.retrofit.service

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import retrofit2.http.GET

interface MovieListService {
    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("/movie/popular")
    suspend fun getPopularMovies(): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("/movie/top_rated")
    suspend fun getTopRatedMovies(): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
    @GET("/movie/upcoming")
    suspend fun getUpcomingMovies(): NetworkResponse<PagingDTO<MoviePreviewDTO>, String>
}