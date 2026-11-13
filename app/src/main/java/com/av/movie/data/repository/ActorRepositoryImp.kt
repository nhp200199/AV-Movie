package com.av.movie.data.repository

import com.av.movie.data.api.ActorApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.ActorDTO
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.map
import com.av.movie.domain.model.Actor
import com.av.movie.domain.model.Credit
import com.av.movie.domain.repository.ActorRepository
import javax.inject.Inject

class ActorRepositoryImp @Inject constructor(
    private val actorApi: ActorApi,
    private val actorMapper: Mapper<ActorDTO, Actor>,
    private val creditMapper: Mapper<CreditsDTO, @JvmSuppressWildcards List<Credit>>
) : ActorRepository {
    override suspend fun getActorInfo(id: Int): ResultData<Actor, String> =
        actorApi.getActorInfo(id).map(actorMapper::map)

    override suspend fun getCreditOfActor(id: Int): ResultData<List<Credit>, String> =
        actorApi.getCreditOfActor(id).map(creditMapper::map)
}
