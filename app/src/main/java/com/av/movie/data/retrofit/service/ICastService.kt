package com.av.movie.data.retrofit.service

import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface ICastService {
    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") id: Int): ResultData<CastListDTO, String>
}