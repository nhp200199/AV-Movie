package com.av.movie.data.api.retrofit.service

import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IActorService {
    @GET("person/{person_id}")
    suspend fun getActorInfo(@Path("person_id") id: Int): NetworkResponse<ActorDTO, String>
}