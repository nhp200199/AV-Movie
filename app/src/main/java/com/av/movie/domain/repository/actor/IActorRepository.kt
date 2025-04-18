package com.av.movie.domain.repository.actor

import com.av.movie.data.model.Actor
import com.av.movie.data.model.ResultData

interface IActorRepository {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
}