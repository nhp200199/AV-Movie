package com.av.movie.data.repository.actor

import com.av.movie.data.api.ActorApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Actor
import com.av.movie.domain.repository.actor.ActorRepository
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val actorApi: ActorApi,
    private val mapper: Mapper<ActorDTO, Actor>
) : ActorRepository {
    override suspend fun getActorInfo(id: Int): ResultData<Actor, String> {
        return actorApi.getActorInfo(id).map(mapper::map)
    }
}
