package com.av.movie.data.datasource.remote.actor

import com.av.movie.data.api.retrofit.service.IActorService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.NetworkResponse
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