package com.av.movie.domain.repository

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Actor
import com.av.movie.domain.model.Credit

interface ActorRepository {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
    suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String>
}
