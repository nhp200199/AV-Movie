package com.av.movie.data.api

import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendations(@Path("movie_id") id: Int): ResultData<PagingDTO<MoviePreviewDTO>, String>

    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path("movie_id") id: Int): ResultData<MovieDetailDTO, String>

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") id: Int): ResultData<CastListDTO, String>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideosForMovie(@Path("movie_id") id: Int): ResultData<VideoListDTO, String>

    @GET("genre/movie/list")
    suspend fun getAllGenres(): ResultData<GenreDTO, String>
}
