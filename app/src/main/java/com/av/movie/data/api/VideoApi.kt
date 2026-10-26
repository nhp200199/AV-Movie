package com.av.movie.data.api

import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface VideoApi {
    @GET("movie/{movie_id}/videos")
    suspend fun getVideosForMovie(@Path("movie_id") id: Int): ResultData<VideoListDTO, String>
}