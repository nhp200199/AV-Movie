package com.av.movie.data.repository.actor

import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.ActorRemoteDataSource
import com.av.movie.domain.model.Actor
import com.av.movie.domain.repository.actor.ActorRepository
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val actorDataSource: ActorRemoteDataSource
) : ActorRepository {
    override suspend fun getActorInfo(id: Int): ResultData<Actor, String> {
        return actorDataSource.getActorInfo(id)
    }
}