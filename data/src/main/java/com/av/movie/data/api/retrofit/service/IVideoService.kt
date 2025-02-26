package com.av.movie.data.api.retrofit.service

import com.av.movie.data.model.NetworkResponse
import com.av.movie.data.model.VideoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface IVideoService {
    @GET("movie/{movie_id}/videos")
    suspend fun getVideosForMovie(@Path("movie_id") id: Int): NetworkResponse<List<VideoDTO>, String>
}