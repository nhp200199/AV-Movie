package com.av.movie.data.retrofit.service

import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ICreditService {
    @GET("person/{person_id}/tv_credits")
    suspend fun getCreditOfActor(@Path("person_id") id: Int): NetworkResponse<CreditsDTO, String>
}