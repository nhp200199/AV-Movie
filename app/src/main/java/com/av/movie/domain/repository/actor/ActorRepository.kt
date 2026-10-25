package com.av.movie.domain.repository.actor

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Actor

interface ActorRepository {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
}