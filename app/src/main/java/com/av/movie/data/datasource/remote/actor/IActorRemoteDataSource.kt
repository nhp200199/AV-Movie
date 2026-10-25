package com.av.movie.data.datasource.remote.actor

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Actor

interface IActorRemoteDataSource : IBaseRemoteDataSource<ActorDTO, Actor> {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
}