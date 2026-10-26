package com.av.movie.data.repository.datasourceimp

import com.av.movie.data.api.ActorApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.repository.datasource.ActorRemoteDataSource
import com.av.movie.domain.model.Actor
import javax.inject.Inject

class ActorRemoteDataSourceImp @Inject constructor(
    private val actorApi: ActorApi,
    mapper: Mapper<ActorDTO, Actor>
) : ActorRemoteDataSource,
    BaseRemoteDataSource<ActorDTO, Actor>(mapper) {

    override suspend fun getActorInfo(id: Int): ResultData<Actor, String> {
        return getData { actorApi.getActorInfo(id) }
    }
}