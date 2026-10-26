package com.av.movie.data.api

import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditApi {
    @GET("person/{person_id}/tv_credits")
    suspend fun getCreditOfActor(@Path("person_id") id: Int): ResultData<CreditsDTO, String>
}