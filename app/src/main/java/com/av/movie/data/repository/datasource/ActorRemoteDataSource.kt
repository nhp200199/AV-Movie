package com.av.movie.data.repository.datasource

import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Actor

interface ActorRemoteDataSource : IBaseRemoteDataSource<ActorDTO, Actor> {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
}