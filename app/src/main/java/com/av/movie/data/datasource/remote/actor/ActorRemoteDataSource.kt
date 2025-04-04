package com.av.movie.data.datasource.remote.actor

import com.av.movie.data.retrofit.service.IActorService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.Mapper
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData

class ActorRemoteDataSource(
    private val actorService: IActorService,
    mapper: Mapper<ActorDTO, Actor>
) : IActorRemoteDataSource,
    BaseRemoteDataSource<ActorDTO, Actor>(mapper) {

    override suspend fun getActorInfo(id: Int): ResultData<Actor> {
        return getData { actorService.getActorInfo(id) }
    }
}