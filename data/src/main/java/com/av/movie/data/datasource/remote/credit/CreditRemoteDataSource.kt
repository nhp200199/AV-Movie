package com.av.movie.data.datasource.remote.credit

import com.av.movie.data.api.retrofit.service.ICreditService
import com.av.movie.data.datasource.remote.BaseRemoteDataSource
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CreditsDTO
import com.av.movie.data.model.ResultData

class CreditRemoteDataSource(
    private val creditService: ICreditService,
    mapper: Mapper<CreditsDTO, CreditsDTO>
) : ICreditRemoteDataSource,
    BaseRemoteDataSource<CreditsDTO, CreditsDTO>(mapper) {

    override suspend fun getCreditOfActor(id: Int): ResultData<CreditsDTO> {
        return getData { creditService.getCreditOfActor(id) }
    }
}