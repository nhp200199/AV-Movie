package com.av.movie.data.retrofit.service

import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface IActorService {
    @GET("person/{person_id}")
    suspend fun getActorInfo(@Path("person_id") id: Int): ResultData<ActorDTO, String>
}