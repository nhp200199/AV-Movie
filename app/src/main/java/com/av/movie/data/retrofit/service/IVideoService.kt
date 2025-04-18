package com.av.movie.data.retrofit.service

import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface IVideoService {
    @GET("movie/{movie_id}/videos")
    suspend fun getVideosForMovie(@Path("movie_id") id: Int): ResultData<VideoListDTO, String>
}