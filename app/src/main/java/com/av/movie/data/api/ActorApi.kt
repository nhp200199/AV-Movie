package com.av.movie.data.api

import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorApi {
    @GET("person/{person_id}")
    suspend fun getActorInfo(@Path("person_id") id: Int): ResultData<ActorDTO, String>

    @GET("person/{person_id}/tv_credits")
    suspend fun getCreditOfActor(@Path("person_id") id: Int): ResultData<CreditsDTO, String>
}
