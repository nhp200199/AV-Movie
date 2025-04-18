package com.av.movie.data.datasource.remote.actor

import com.av.movie.data.datasource.remote.IBaseRemoteDataSource
import com.av.movie.data.model.Actor
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.ResultData

interface IActorRemoteDataSource : IBaseRemoteDataSource<ActorDTO, Actor> {
    suspend fun getActorInfo(id: Int): ResultData<Actor, String>
}