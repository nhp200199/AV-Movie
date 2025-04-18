package com.av.movie.domain.repository.actor

import com.av.movie.data.datasource.remote.actor.IActorRemoteDataSource
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ResultData
import javax.inject.Inject

class ActorRepository @Inject constructor(
    private val actorDataSource: IActorRemoteDataSource
) : IActorRepository {
    override suspend fun getActorInfo(id: Int): ResultData<Actor, String> {
        return actorDataSource.getActorInfo(id)
    }
}